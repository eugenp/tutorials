import { Page, Locator } from "@playwright/test";
import { SearchResultPage } from "./search-result-page";

export class HomePage {
  readonly myAccountLink: Locator;
  readonly registerLink: Locator;
  readonly searchProductField: Locator;
  readonly searchBtn: Locator;
  readonly logoutLink: Locator;
  readonly page: Page;

  constructor(page: Page) {
    this.page = page;
    this.myAccountLink = page.getByRole("button", { name: " My account" });
    this.registerLink = page.getByRole("link", { name: "Register" });
    this.logoutLink = page.getByRole("link", { name: " Logout" });
    this.searchProductField = page.getByPlaceholder("Search For Products");
    this.searchBtn = page.getByRole("button", { name: "Search" });
  }

  async hoverMyAccountLink():Promise<void> {
    await this.myAccountLink.hover({ force: true });
  }
  async navigateToRegistrationPage(): Promise<void>{
    await this.hoverMyAccountLink();
    await this.registerLink.click();
  }

  async searchForProduct(productName: string): Promise<SearchResultPage> {
    await this.searchProductField.first().fill(productName);
    await this.searchBtn.click();
    return new SearchResultPage(this.page);
  }
}