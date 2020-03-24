import { RestClientPage } from './app.po';

describe('rest-client App', () => {
  let page: RestClientPage;

  beforeEach(() => {
    page = new RestClientPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!!');
  });
});
