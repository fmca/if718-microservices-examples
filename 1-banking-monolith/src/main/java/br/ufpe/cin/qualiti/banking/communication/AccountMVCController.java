package br.ufpe.cin.qualiti.banking.communication;

import br.ufpe.cin.qualiti.banking.model.Facade;
import br.ufpe.cin.qualiti.banking.model.account.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountMVCController {

    @Autowired private Facade facade;

    @Value("${base_url}")
    private String baseUrl;

    @ModelAttribute("baseUrl")
    public String getUrl() {
        return this.baseUrl;
    }

    @GetMapping("")
    public String getAccounts(Model model) {
        model.addAttribute("accounts", facade.getAccounts());
        return "accounts";
    }

    @GetMapping("/form")
    public String getAccountForm() {
        return "account_form";
    }

    @PostMapping("/form")
    public String newAccount(@ModelAttribute AccountDTO accountDTO) {
        facade.createAccount(accountDTO);
        return "redirect:/account";
    }
}
