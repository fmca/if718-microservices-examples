package br.ufpe.cin.qualiti.banking.communication;

import br.ufpe.cin.qualiti.banking.model.account.AccountController;
import br.ufpe.cin.qualiti.banking.model.account.AccountDTO;
import java.util.Collections;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/account")
@Slf4j
public class AccountMVCController {

    @Autowired private AccountController controller;

    @GetMapping("")
    public String getAccounts(Model model) {
        model.addAttribute("accounts", controller.getAll());
        return "accounts";
    }

    @GetMapping("/form")
    public String getAccountForm() {
        return "account_form";
    }

    @PostMapping("/form")
    public String newAccount(@ModelAttribute AccountDTO accountDTO) {
        controller.create(accountDTO);
        return "redirect:/account";
    }

    @GetMapping("/{id}/enoughBalance/{value}")
    @ResponseBody
    public Map<String, Boolean> enoughBalance(@PathVariable Long id, @PathVariable Long value) {
        return Collections.singletonMap("enoughBalance", controller.enoughBalance(id, value));
    }

    @PutMapping("/{id}/balance")
    @ResponseBody
    public void updateBalance(@PathVariable Long id, @RequestBody Long value) {
        log.info(String.format("%s -> %s", id, value));
        controller.updateBalance(id, value);
    }
}
