package allPages;

import dataHelp.DataHelper;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class CardPage {
    private SelenideElement amount;

    {
        amount = $("h1");
    }

    private SelenideElement sumAmount = $("[data-test-id='amount'] input");
    private SelenideElement sumFrom = $("[data-test-id='from'] input");
    private SelenideElement buttonFrom = $("[data-test-id='action-transfer']");
    private SelenideElement transferOverMax = $("[data-test-id='action-transfer']");

    public CardPage() {

        amount.shouldBe(visible);
    }

    public static CardPage transferOverMax() {
        return null;
    }
    public DashboardPage card(DataHelper.CardsInfo cardsInfo) {
        sumAmount.setValue(String.valueOf(cardsInfo.getSum()));
        sumFrom.setValue(cardsInfo.getNumCard());
        buttonFrom.click();
        return new DashboardPage();
    }
    }
