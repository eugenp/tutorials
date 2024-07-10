import * as Gestures from '@vaadin/component-base/src/gestures.js';

(function () {
  function tryCatchWrapper(callback) {
    return window.Vaadin.Flow.tryCatchWrapper(callback, 'Vaadin Context Menu Target');
  }

  function init(target) {
    if (target.$contextMenuTargetConnector) {
      return;
    }

    target.$contextMenuTargetConnector = {
      openOnHandler: tryCatchWrapper(function (e) {
        // used by Grid to prevent context menu on selection column click
        if (target.preventContextMenu && target.preventContextMenu(e)) {
          return;
        }
        e.preventDefault();
        e.stopPropagation();
        this.$contextMenuTargetConnector.openEvent = e;
        let detail = {};
        if (target.getContextMenuBeforeOpenDetail) {
          detail = target.getContextMenuBeforeOpenDetail(e);
        }
        target.dispatchEvent(
          new CustomEvent('vaadin-context-menu-before-open', {
            detail: detail
          })
        );
      }),

      updateOpenOn: tryCatchWrapper(function (eventType) {
        this.removeListener();
        this.openOnEventType = eventType;

        customElements.whenDefined('vaadin-context-menu').then(
          tryCatchWrapper(() => {
            if (Gestures.gestures[eventType]) {
              Gestures.addListener(target, eventType, this.openOnHandler);
            } else {
              target.addEventListener(eventType, this.openOnHandler);
            }
          })
        );
      }),

      removeListener: tryCatchWrapper(function () {
        if (this.openOnEventType) {
          if (Gestures.gestures[this.openOnEventType]) {
            Gestures.removeListener(target, this.openOnEventType, this.openOnHandler);
          } else {
            target.removeEventListener(this.openOnEventType, this.openOnHandler);
          }
        }
      }),

      openMenu: tryCatchWrapper(function (contextMenu) {
        contextMenu.open(this.openEvent);
      }),

      removeConnector: tryCatchWrapper(function () {
        this.removeListener();
        target.$contextMenuTargetConnector = undefined;
      })
    };
  }

  window.Vaadin.Flow.contextMenuTargetConnector = {
    init(...args) {
      return tryCatchWrapper(init)(...args);
    }
  };
})();
