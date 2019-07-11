package net.thumbtack.onlineshop.controller;

import net.thumbtack.onlineshop.controller.validation.ValidationException;
import net.thumbtack.onlineshop.database.models.Basket;
import net.thumbtack.onlineshop.dto.AccountDto;
import net.thumbtack.onlineshop.dto.DepositDto;
import net.thumbtack.onlineshop.dto.ProductDto;
import net.thumbtack.onlineshop.dto.ResultBasketDto;
import net.thumbtack.onlineshop.service.AccountService;
import net.thumbtack.onlineshop.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api")
public class ClientController {

    private ClientService clientService;
    private AccountService accountService;

    @Autowired
    public ClientController(ClientService clientService, AccountService accountService) {
        this.clientService = clientService;
        this.accountService = accountService;
    }

    @PutMapping("deposits")
    @ResponseStatus(HttpStatus.OK)
    public AccountDto putDeposit(
            @CookieValue("JAVASESSIONID") String session,
            @RequestBody @Valid DepositDto deposit,
            BindingResult result) throws Exception {

        if (result.hasErrors())
            throw new ValidationException(result);

        clientService.putDeposit(session, deposit.getDeposit());

        return new AccountDto(
                accountService.getAccount(session)
        );
    }

    @GetMapping("deposits")
    @ResponseStatus(HttpStatus.OK)
    public AccountDto getDeposit(
            @CookieValue("JAVASESSIONID") String session) throws Exception {

        // TODO: Удалить из AccountService метод getDeposit
        return new AccountDto(
                accountService.getAccount(session)
        );
    }

    @PostMapping("purchases")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto buyProduct(
            @CookieValue("JAVASESSIONID") String session,
            @RequestBody @Valid ProductDto product,
            BindingResult result) throws Exception {

        if (result.hasErrors())
            throw new ValidationException(result);

        if (product.getCount() == null)
            product.setCount(1);

        return clientService.buyProduct(session, product);
    }

    @PostMapping("baskets")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> addToBasket(
            @CookieValue("JAVASESSIONID") String session,
            @RequestBody @Valid ProductDto product,
            BindingResult result) throws Exception {

        if (result.hasErrors())
            throw new ValidationException(result);

        if (product.getCount() == null)
            product.setCount(1);

        List<Basket> basket = clientService.addToBasket(session, product);

        return getBasket(basket);
    }


    @DeleteMapping("baskets/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteFromBasket(
            @CookieValue("JAVASESSIONID") String session,
            @PathVariable int id) throws Exception {

        clientService.deleteFromBasket(session, id);

        return "{}";
    }

    // TODO: Здесь поле количества должно быть обязательным
    @PutMapping("baskets")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> editProductCount(
            @CookieValue("JAVASESSIONID") String session,
            @RequestBody @Valid ProductDto product,
            BindingResult result) throws Exception {

        if (result.hasErrors())
            throw new ValidationException(result);

        List<Basket> basket =  clientService.editProductCount(session, product);

        return getBasket(basket);
    }

    @GetMapping("baskets")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> getBasket(
            @CookieValue("JAVASESSIONID") String session) throws Exception {

        return getBasket(clientService.getBasket(session));

    }

    @PostMapping("purchases/baskets")
    @ResponseStatus(HttpStatus.OK)
    public ResultBasketDto buyBasket(
            @CookieValue("JAVASESSIONID") String session,
            @RequestBody @Valid List<ProductDto> toBuy,
            BindingResult result) throws Exception {

        if (result.hasErrors())
            throw new ValidationException(result);

        Pair<List<ProductDto>, List<Basket>> basket = clientService.buyBasket(session, toBuy);

        return new ResultBasketDto(basket.getFirst(), basket.getSecond());
    }

    private List<ProductDto> getBasket(List<Basket> basket) {
        List<ProductDto> result = new ArrayList<>();

        basket.forEach((entity) -> result.add(new ProductDto(entity)));
        return result;
    }

}
