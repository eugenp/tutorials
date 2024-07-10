(function () {
  const tryCatchWrapper = function (callback) {
    return window.Vaadin.Flow.tryCatchWrapper(callback, 'Vaadin Select');
  };

  window.Vaadin.Flow.selectConnector = {
    initLazy: (select) =>
      tryCatchWrapper(function (select) {
        const _findListBoxElement = tryCatchWrapper(function () {
          for (let i = 0; i < select.childElementCount; i++) {
            const child = select.children[i];
            if ('VAADIN-SELECT-LIST-BOX' === child.tagName.toUpperCase()) {
              return child;
            }
          }
        });

        // do not init this connector twice for the given select
        if (select.$connector) {
          return;
        }

        select.$connector = {};

        select.renderer = tryCatchWrapper(function (root) {
          const listBox = _findListBoxElement();
          if (listBox) {
            if (root.firstChild) {
              root.removeChild(root.firstChild);
            }
            root.appendChild(listBox);
          }
        });
      })(select)
  };
})();
