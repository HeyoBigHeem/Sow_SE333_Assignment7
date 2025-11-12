package playwrightTraditional;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;
import org.junit.jupiter.api.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.nio.file.Paths;
import java.util.*;

public class BookstoreTest {
    private static Playwright playwright;
    private static Browser browser;
    private BrowserContext context;
    private Page page;

    @BeforeAll
    public static void setUpAll() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
    }

    @BeforeEach
    public void setUp() {
        context = browser.newContext(new Browser.NewContextOptions().setRecordVideoDir(Paths.get("videos/")).setRecordVideoSize(1280, 720));
        page = context.newPage();
        page.navigate("https://depaul.bncollege.com/");
    }

    @AfterEach
    public void tearDown() {
        if (context != null) {
            context.close();
        }
    }

    @AfterAll
    public static void tearDownAll() {
        if (browser != null) { browser.close(); }
        if (playwright != null) { playwright.close(); }
    }

    @Test
    public void testEntireSite() {
    // tests Bookstore
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search")).fill("earbuds");
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search")).press("Enter");

        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("brand")).click();
        page.locator("#facet-brand").getByRole(AriaRole.LIST).getByText("brand JBL").click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Color")).click();
        page.getByText("Color Black").click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Price")).click();
        page.getByText("Price Over $").click();

        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("JBL Quantum True Wireless")).click();

        assertThat(page.getByLabel("main").getByRole(AriaRole.HEADING).first()).containsText("JBL Quantum True Wireless Noise Cancelling Gaming Earbuds- Black");        assertThat(page.getByLabel("main")).containsText("sku 668972707");
        assertThat(page.getByLabel("main")).containsText("$164.98");
        assertThat(page.getByLabel("main")).containsText("Adaptive noise cancelling allows awareness of environment");

        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add to cart")).click();

        assertThat(page.locator("#headerDesktopView")).containsText("Cart 1 items");

        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Cart 1 items")).click();

    // tests ShoppingCart
        assertThat(page.getByLabel("main")).containsText("Your Shopping Cart");
        assertThat(page.getByLabel("main")).containsText("JBL Quantum True Wireless Noise Cancelling Gaming Earbuds- Black");
        assertThat(page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Quantity, edit and press"))).hasValue("1");
        assertThat(page.getByLabel("main")).containsText("$164.98");

        page.getByText("FAST In-Store PickupDePaul").click();
        assertThat(page.getByLabel("main")).containsText("Subtotal $164.98");
        assertThat(page.getByLabel("main")).containsText("Handling");
        assertThat(page.getByLabel("main")).containsText("Taxes TBD");
        assertThat(page.getByLabel("main")).containsText("Estimated Total $167.98");

        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter Promo Code")).fill("TEST");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Apply Promo Code")).click();
        assertThat(page.locator("#js-voucher-result")).containsText("The coupon code entered is not valid");

        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Proceed To Checkout")).first().click();

    // tests CreateAccount
        assertThat(page.getByLabel("main")).containsText("Create Account");
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Proceed As Guest")).click();

    // tests ContactInformation
        assertThat(page.getByLabel("main")).containsText("Contact Information");

        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("First Name (required)")).fill("Ima");
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Last Name (required)")).fill("Passdisclass");
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Email address (required)")).fill("imapassdisclass@yayme.com");
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Phone Number (required)")).fill("7732025000");

        assertThat(page.getByLabel("main")).containsText("Order Subtotal $164.98");
        assertThat(page.getByLabel("main")).containsText("Handling");
        assertThat(page.getByLabel("main")).containsText("Tax TBD");
        assertThat(page.getByLabel("main")).containsText("Total $167.98");

        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();

    // tests PickupInformation
        assertThat(page.getByLabel("main")).containsText("Ima Passdisclass");
        assertThat(page.getByLabel("main")).containsText("imapassdisclass@yayme.com");
        assertThat(page.getByLabel("main")).containsText("+17732025000");
        assertThat(page.locator("#bnedPickupPersonForm")).containsText("DePaul University Loop Campus & SAIC");
        assertThat(page.locator("label").filter(new Locator.FilterOptions().setHasText("I'll pick them up")).locator("span").first()).isVisible();

        assertThat(page.getByLabel("main")).containsText("Order Subtotal $164.98");
        assertThat(page.getByLabel("main")).containsText("Handling");
        assertThat(page.getByLabel("main")).containsText("Tax TBD");
        assertThat(page.getByLabel("main")).containsText("Total $167.98");

        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();

    // tests PaymentInformation
        assertThat(page.getByLabel("main")).containsText("Order Subtotal $164.98");
        assertThat(page.getByLabel("main")).containsText("Handling");
        assertThat(page.getByLabel("main")).containsText("Tax TBD");
        assertThat(page.getByLabel("main")).containsText("Total $167.98");
        assertThat(page.getByLabel("main")).containsText("JBL Quantum True Wireless Noise Cancelling Gaming Earbuds- Black Quantity: Qty: 1 $164.98");

        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Back to cart")).click();

    // tests RemoveFromCart
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Remove product JBL Quantum")).click();
        assertThat(page.getByRole(AriaRole.ALERT)).containsText("Product has been removed from your cart.");
        assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Your cart is empty"))).isVisible();
    }
}
