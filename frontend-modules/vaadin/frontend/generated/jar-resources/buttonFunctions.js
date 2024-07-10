function disableOnClickListener({currentTarget: button}) {
  button.disabled = button.hasAttribute('disableOnClick');
}

window.Vaadin.Flow.button = {
  initDisableOnClick: (button) => {
    if (!button.__hasDisableOnClickListener) {
      button.addEventListener('click', disableOnClickListener);
      button.__hasDisableOnClickListener = true;
    }
  }
}
