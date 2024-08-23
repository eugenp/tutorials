import { Page, Locator } from "@playwright/test";

export class MyAccountPage {
  readonly pageHeader: Locator;

  constructor(page: Page) {
    this.pageHeader = page.getByText(" Your Account Has Been Created!");
  }
}