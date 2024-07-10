window.Vaadin = window.Vaadin || {};
window.Vaadin.Flow = window.Vaadin.Flow || {};
window.Vaadin.Flow.dndConnector = {
  __ondragenterListener: function (event) {
    // TODO filter by data type
    // TODO prevent dropping on itself (by default)
    const effect = event.currentTarget['__dropEffect'];
    if (!event.currentTarget.hasAttribute('disabled')) {
      if (effect) {
        event.dataTransfer.dropEffect = effect;
      }

      if (effect !== 'none') {
        /* #7108: if drag moves on top of drop target's children, first another ondragenter event
         * is fired and then a ondragleave event. This happens again once the drag
         * moves on top of another children, or back on top of the drop target element.
         * Thus need to "cancel" the following ondragleave, to not remove class name.
         * Drop event will happen even when dropped to a child element. */
        if (event.currentTarget.classList.contains('v-drag-over-target')) {
          event.currentTarget['__skip-leave'] = true;
        } else {
          event.currentTarget.classList.add('v-drag-over-target');
        }
        // enables browser specific pseudo classes (at least FF)
        event.preventDefault();
        event.stopPropagation(); // don't let parents know
      }
    }
  },

  __ondragoverListener: function (event) {
    // TODO filter by data type
    // TODO filter by effectAllowed != dropEffect due to Safari & IE11 ?
    if (!event.currentTarget.hasAttribute('disabled')) {
      const effect = event.currentTarget['__dropEffect'];
      if (effect) {
        event.dataTransfer.dropEffect = effect;
      }
      // allows the drop && don't let parents know
      event.preventDefault();
      event.stopPropagation();
    }
  },

  __ondragleaveListener: function (event) {
    if (event.currentTarget['__skip-leave']) {
      event.currentTarget['__skip-leave'] = false;
    } else {
      event.currentTarget.classList.remove('v-drag-over-target');
    }
    // #7109 need to stop or any parent drop target might not get highlighted,
    // as ondragenter for it is fired before the child gets dragleave.
    event.stopPropagation();
  },

  __ondropListener: function (event) {
    const effect = event.currentTarget['__dropEffect'];
    if (effect) {
      event.dataTransfer.dropEffect = effect;
    }
    event.currentTarget.classList.remove('v-drag-over-target');
    // prevent browser handling && don't let parents know
    event.preventDefault();
    event.stopPropagation();
  },

  updateDropTarget: function (element) {
    if (element['__active']) {
      element.addEventListener('dragenter', this.__ondragenterListener, false);
      element.addEventListener('dragover', this.__ondragoverListener, false);
      element.addEventListener('dragleave', this.__ondragleaveListener, false);
      element.addEventListener('drop', this.__ondropListener, false);
    } else {
      element.removeEventListener('dragenter', this.__ondragenterListener, false);
      element.removeEventListener('dragover', this.__ondragoverListener, false);
      element.removeEventListener('dragleave', this.__ondragleaveListener, false);
      element.removeEventListener('drop', this.__ondropListener, false);
      element.classList.remove('v-drag-over-target');
    }
  },

  /** DRAG SOURCE METHODS: */

  __dragstartListener: function (event) {
    event.stopPropagation();
    event.dataTransfer.setData('text/plain', '');
    if (event.currentTarget.hasAttribute('disabled')) {
      event.preventDefault();
    } else {
      if (event.currentTarget['__effectAllowed']) {
        event.dataTransfer.effectAllowed = event.currentTarget['__effectAllowed'];
      }
      event.currentTarget.classList.add('v-dragged');
    }
  },

  __dragendListener: function (event) {
    event.currentTarget.classList.remove('v-dragged');
  },

  updateDragSource: function (element) {
    if (element['draggable']) {
      element.addEventListener('dragstart', this.__dragstartListener, false);
      element.addEventListener('dragend', this.__dragendListener, false);
    } else {
      element.removeEventListener('dragstart', this.__dragstartListener, false);
      element.removeEventListener('dragend', this.__dragendListener, false);
    }
  }
};
