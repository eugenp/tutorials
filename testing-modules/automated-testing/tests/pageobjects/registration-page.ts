import { Page, Locator } from "@playwright/test";
import { MyAccountPage } from "./my-account-page";

export class RegistrationPage {
  readonly pageTitle: Locator;
  readonly firstNameField: Locator;
  readonly lastNameField: Locator;
  readonly emailField: Locator;
  readonly telephoneField: Locator;
  readonly passwordField: Locator;
  readonly confirmPassword: Locator;
  readonly agreePolicy: Locator;
  readonly continueBtn: Locator;
  readonly page: Page;

  constructor(page: Page) {
    this.page = page;
    this.pageTitle = page.getByText("Register Account");
    this.firstNameField = page.getByLabel("First Name");
    this.lastNameField = page.getByLabel("Last Name");
    this.emailField = page.getByPlaceholder("E-Mail");
    this.telephoneField = page.getByLabel("Telephone");
    this.passwordField = page.getByPlaceholder("Password", { exact: true });
    this.confirmPassword = page.getByPlaceholder("Password Confirm", {
      exact: true,
    });
    this.agreePolicy = page.locator("div.float-right div.custom-control");
    this.continueBtn = page.locator("input.btn");
  }

  async registerUser(
    firstName: string,
    lastName: string,
    email: string,
    telephoneNumber: string,
    password: string
  ): Promise<MyAccountPage> {
    await this.firstNameField.fill(firstName);
    await this.lastNameField.fill(lastName);
    await this.emailField.fill(email);
    await this.telephoneField.fill(telephoneNumber);
    await this.passwordField.fill(password);
    await this.confirmPassword.fill(password);
    await this.agreePolicy.click();
    await this.continueBtn.click();

    return new MyAccountPage(this.page);
  }
}