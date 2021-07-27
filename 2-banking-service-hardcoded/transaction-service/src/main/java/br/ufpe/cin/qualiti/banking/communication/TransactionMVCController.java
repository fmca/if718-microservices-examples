package br.ufpe.cin.qualiti.banking.communication;

import br.ufpe.cin.qualiti.banking.model.transaction.NotEnoughBalanceException;
import br.ufpe.cin.qualiti.banking.model.transaction.TransactionController;
import br.ufpe.cin.qualiti.banking.model.transaction.TransactionDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/transaction")
@Slf4j
public class TransactionMVCController {

    @Autowired private TransactionController transacationController;

    @Value("${base_url}")
    private String baseUrl;

    @ModelAttribute("baseUrl")
    public String getUrl() {
        return this.baseUrl;
    }

    @GetMapping("/account/{accountId}")
    public String getTransactions(@PathVariable(name = "accountId") Long accountId, Model model) {
        model.addAttribute("transactions", transacationController.getAllFrom(accountId));
        return "transactions";
    }

    @GetMapping("/form")
    public String getTransactionForm() {
        return "transaction_form";
    }

    @PostMapping("/form")
    public String newTransaction(
            @ModelAttribute TransactionDTO transactionDTO, RedirectAttributes redirectAttributes) {
        log.info(transactionDTO.toString());
        try {
            transacationController.doTransaction(transactionDTO);
        } catch (NotEnoughBalanceException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/transaction/form";
        }
        return "redirect:/transaction/account/" + transactionDTO.getFromAccountId();
    }
}
