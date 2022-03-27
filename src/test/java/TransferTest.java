import AllPages.CardPage;
import AllPages.DashboardPage;
import AllPages.LoginPage;
import DataHelp.DataHelper;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.*;

class TransferTest {

    int amount = 1900;
    int maxSum = 100000;

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldTransferBetweenCards() {
        var dashboardPage = new DashboardPage();
        var balanceFirstBefore = dashboardPage.getCardBalance(0);
        var balanceSecondBefore = dashboardPage.getCardBalance(1);
        var balanceFirstAfter = balanceFirstBefore + amount;
        var balanceSecondAfter = balanceSecondBefore - amount;
        var verificationFirstCard = DataHelper.getFirstCardsInfo(amount);
        dashboardPage.personFirstCard().card(verificationFirstCard);

        assertEquals(balanceFirstAfter, dashboardPage.getCardBalance(0));
        assertEquals(balanceSecondAfter, dashboardPage.getCardBalance(1));
    }

    @Test
    void shouldTransferInBack() {
        var dashboardPage = new DashboardPage();
        var balanceFirstBefore = dashboardPage.getCardBalance(0);
        var balanceSecondBefore = dashboardPage.getCardBalance(1);
        var balanceFirstAfter = balanceFirstBefore - amount;
        var balanceSecondAfter = balanceSecondBefore + amount;
        var verificationSecondCard = DataHelper.getSecondCardsInfo(amount);
        dashboardPage.personSecondCard().card(verificationSecondCard);

        assertEquals(balanceFirstAfter, dashboardPage.getCardBalance(0));
        assertEquals(balanceSecondAfter, dashboardPage.getCardBalance(1));
    }

    @Test
    void shouldTrnsferOverLimitNegative() {
        var dashboardPage = new DashboardPage();
        var balanceFirstBefore = dashboardPage.getCardBalance(0);
        var balanceSecondBefore = dashboardPage.getCardBalance(1);
        var balanceFirstAfter = balanceFirstBefore - maxSum;
        var balanceSecondAfter = balanceSecondBefore + maxSum;
        var verificationSecondCard = DataHelper.getSecondCardsInfo(maxSum);
        dashboardPage.personSecondCard().card(verificationSecondCard);

        assertEquals(balanceFirstAfter, dashboardPage.getCardBalance(0));
        assertEquals(balanceSecondAfter, dashboardPage.getCardBalance(1));
    }
    @Test
    void shouldTransferOver() {
        var dashboardPage = new DashboardPage();
        var balanceFirstBefore = dashboardPage.getCardBalance(0);
        var balanceFirstAfter = balanceFirstBefore - amount;;
        var verificationSecondCard = DataHelper.getSecondCardsInfo(amount);
        CardPage.transferOverMax().card(verificationSecondCard);
        assertEquals(balanceFirstAfter, dashboardPage.getCardBalance(0));

    }
}
