(function () {
  function tryCatchWrapper(callback) {
    return window.Vaadin.Flow.tryCatchWrapper(callback, 'Vaadin Context Menu');
  }

  function getContainer(appId, nodeId) {
    try {
      return window.Vaadin.Flow.clients[appId].getByNodeId(nodeId);
    } catch (error) {
      console.error('Could not get node %s from app %s', nodeId, appId);
      console.error(error);
    }
  }

  /**
   * Initializes the connector for a context menu element.
   *
   * @param {HTMLElement} contextMenu
   * @param {string} appId
   */
  function initLazy(contextMenu, appId) {
    if (contextMenu.$connector) {
      return;
    }

    contextMenu.$connector = {
      /**
       * Generates and assigns the items to the context menu.
       *
       * @param {number} nodeId
       */
      generateItems: tryCatchWrapper((nodeId) => {
        const items = generateItemsTree(appId, nodeId);

        contextMenu.items = items;
      })
    };
  }

  /**
   * Generates an items tree compatible with the context-menu web component
   * by traversing the given Flow DOM tree of context menu item nodes
   * whose root node is identified by the `nodeId` argument.
   *
   * The app id is required to access the store of Flow DOM nodes.
   *
   * @param {string} appId
   * @param {number} nodeId
   */
  function generateItemsTree(appId, nodeId) {
    const container = getContainer(appId, nodeId);
    if (!container) {
      return;
    }

    return Array.from(container.children).map((child) => {
      const item = {
        component: child,
        checked: child._checked,
        keepOpen: child._keepOpen,
        className: child.className,
        theme: child.__theme
      };
      // Do not hardcode tag name to allow `vaadin-menu-bar-item`
      if (child._hasVaadinItemMixin && child._containerNodeId) {
        item.children = generateItemsTree(appId, child._containerNodeId);
      }
      child._item = item;
      return item;
    });
  }

  /**
   * Sets the checked state for a context menu item.
   *
   * This method is supposed to be called when the context menu item is closed,
   * so there is no need for triggering a re-render eagarly.
   *
   * @param {HTMLElement} component
   * @param {boolean} checked
   */
  function setChecked(component, checked) {
    if (component._item) {
      component._item.checked = checked;

      // Set the attribute in the connector to show the checkmark
      // without having to re-render the whole menu while opened.
      if (component._item.keepOpen) {
        component.toggleAttribute('menu-item-checked', checked);
      }
    }
  }

  /**
   * Sets the keep open state for a context menu item.
   *
   * @param {HTMLElement} component
   * @param {boolean} keepOpen
   */
  function setKeepOpen(component, keepOpen) {
    if (component._item) {
      component._item.keepOpen = keepOpen;
    }
  }

  /**
   * Sets the theme for a context menu item.
   *
   * This method is supposed to be called when the context menu item is closed,
   * so there is no need for triggering a re-render eagarly.
   *
   * @param {HTMLElement} component
   * @param {string | undefined | null} theme
   */
  function setTheme(component, theme) {
    if (component._item) {
      component._item.theme = theme;
    }
  }

  window.Vaadin.Flow.contextMenuConnector = {
    initLazy(...args) {
      return tryCatchWrapper(initLazy)(...args);
    },

    generateItemsTree(...args) {
      return tryCatchWrapper(generateItemsTree)(...args);
    },

    setChecked(...args) {
      return tryCatchWrapper(setChecked)(...args);
    },

    setKeepOpen(...args) {
      return tryCatchWrapper(setKeepOpen)(...args);
    },

    setTheme(...args) {
      return tryCatchWrapper(setTheme)(...args);
    }
  };
})();
