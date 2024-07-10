// @ts-nocheck
import { Debouncer } from '@polymer/polymer/lib/utils/debounce.js';
import { timeOut, animationFrame } from '@polymer/polymer/lib/utils/async.js';
import { Grid } from '@vaadin/grid/src/vaadin-grid.js';
import { isFocusable } from '@vaadin/grid/src/vaadin-grid-active-item-mixin.js';
import { GridFlowSelectionColumn } from "./vaadin-grid-flow-selection-column.js";

(function () {
  const tryCatchWrapper = function (callback) {
    return window.Vaadin.Flow.tryCatchWrapper(callback, 'Vaadin Grid');
  };

  window.Vaadin.Flow.gridConnector = {
    initLazy: (grid) =>
      tryCatchWrapper(function (grid) {
        // Check whether the connector was already initialized for the grid
        if (grid.$connector) {
          return;
        }

        const dataProviderController = grid._dataProviderController;

        dataProviderController.ensureFlatIndexHierarchyOriginal = dataProviderController.ensureFlatIndexHierarchy;
        dataProviderController.ensureFlatIndexHierarchy = tryCatchWrapper(function (flatIndex) {
          const { item } = this.getFlatIndexContext(flatIndex);
          if (!item || !this.isExpanded(item)) {
            return;
          }

          const isCached = grid.$connector.hasCacheForParentKey(grid.getItemId(item));
          if (isCached) {
            // The sub-cache items are already in the connector's cache. Skip the debouncing process.
            this.ensureFlatIndexHierarchyOriginal(flatIndex);
          } else {
            grid.$connector.beforeEnsureFlatIndexHierarchy(flatIndex, item);
          }
        });

        dataProviderController.isLoadingOriginal = dataProviderController.isLoading;
        dataProviderController.isLoading = tryCatchWrapper(function () {
          return grid.$connector.hasEnsureSubCacheQueue() || this.isLoadingOriginal();
        });

        const rootPageCallbacks = {};
        const treePageCallbacks = {};
        const cache = {};

        /* parentRequestDelay - optimizes parent requests by batching several requests
         *  into one request. Delay in milliseconds. Disable by setting to 0.
         *  parentRequestBatchMaxSize - maximum size of the batch.
         */
        const parentRequestDelay = 50;
        const parentRequestBatchMaxSize = 20;

        let parentRequestQueue = [];
        let parentRequestDebouncer;
        let ensureSubCacheQueue = [];
        let ensureSubCacheDebouncer;

        const rootRequestDelay = 150;
        let rootRequestDebouncer;

        let lastRequestedRanges = {};
        const root = 'null';
        lastRequestedRanges[root] = [0, 0];

        const validSelectionModes = ['SINGLE', 'NONE', 'MULTI'];
        let selectedKeys = {};
        let selectionMode = 'SINGLE';

        let sorterDirectionsSetFromServer = false;

        grid.size = 0; // To avoid NaN here and there before we get proper data
        grid.itemIdPath = 'key';

        function createEmptyItemFromKey(key) {
          return { [grid.itemIdPath]: key };
        }

        grid.$connector = {};

        grid.$connector.hasCacheForParentKey = tryCatchWrapper((parentKey) => cache[parentKey]?.size !== undefined);

        grid.$connector.hasEnsureSubCacheQueue = tryCatchWrapper(() => ensureSubCacheQueue.length > 0);

        grid.$connector.hasParentRequestQueue = tryCatchWrapper(() => parentRequestQueue.length > 0);

        grid.$connector.hasRootRequestQueue = tryCatchWrapper(() => {
          return Object.keys(rootPageCallbacks).length > 0 || !!rootRequestDebouncer?.isActive();
        });

        grid.$connector.beforeEnsureFlatIndexHierarchy = tryCatchWrapper(function (flatIndex, item) {
          // add call to queue
          ensureSubCacheQueue.push({
            flatIndex,
            itemkey: grid.getItemId(item)
          });

          ensureSubCacheDebouncer = Debouncer.debounce(ensureSubCacheDebouncer, animationFrame, () => {
            while (ensureSubCacheQueue.length) {
              grid.$connector.flushEnsureSubCache();
            }
          });
        });

        grid.$connector.doSelection = tryCatchWrapper(function (items, userOriginated) {
          if (selectionMode === 'NONE' || !items.length || (userOriginated && grid.hasAttribute('disabled'))) {
            return;
          }
          if (selectionMode === 'SINGLE') {
            selectedKeys = {};
          }

          items.forEach((item) => {
            if (item) {
              selectedKeys[item.key] = item;
              item.selected = true;
              if (userOriginated) {
                grid.$server.select(item.key);
              }
            }

            // FYI: In single selection mode, the server can send items = [null]
            // which means a "Deselect All" command.
            const isSelectedItemDifferentOrNull = !grid.activeItem || !item || item.key != grid.activeItem.key;
            if (!userOriginated && selectionMode === 'SINGLE' && isSelectedItemDifferentOrNull) {
              grid.activeItem = item;
            }
          });

          grid.selectedItems = Object.values(selectedKeys);
        });

        grid.$connector.doDeselection = tryCatchWrapper(function (items, userOriginated) {
          if (selectionMode === 'NONE' || !items.length || (userOriginated && grid.hasAttribute('disabled'))) {
            return;
          }

          const updatedSelectedItems = grid.selectedItems.slice();
          while (items.length) {
            const itemToDeselect = items.shift();
            for (let i = 0; i < updatedSelectedItems.length; i++) {
              const selectedItem = updatedSelectedItems[i];
              if (itemToDeselect?.key === selectedItem.key) {
                updatedSelectedItems.splice(i, 1);
                break;
              }
            }
            if (itemToDeselect) {
              delete selectedKeys[itemToDeselect.key];
              delete itemToDeselect.selected;
              if (userOriginated) {
                grid.$server.deselect(itemToDeselect.key);
              }
            }
          }
          grid.selectedItems = updatedSelectedItems;
        });

        grid.__activeItemChanged = tryCatchWrapper(function (newVal, oldVal) {
          if (selectionMode != 'SINGLE') {
            return;
          }
          if (!newVal) {
            if (oldVal && selectedKeys[oldVal.key]) {
              if (grid.__deselectDisallowed) {
                grid.activeItem = oldVal;
              } else {
                grid.$connector.doDeselection([oldVal], true);
              }
            }
          } else if (!selectedKeys[newVal.key]) {
            grid.$connector.doSelection([newVal], true);
          }
        });
        grid._createPropertyObserver('activeItem', '__activeItemChanged', true);

        grid.__activeItemChangedDetails = tryCatchWrapper(function (newVal, oldVal) {
          if (grid.__disallowDetailsOnClick) {
            return;
          }
          // when grid is attached, newVal is not set and oldVal is undefined
          // do nothing
          if (newVal == null && oldVal === undefined) {
            return;
          }
          if (newVal && !newVal.detailsOpened) {
            grid.$server.setDetailsVisible(newVal.key);
          } else {
            grid.$server.setDetailsVisible(null);
          }
        });
        grid._createPropertyObserver('activeItem', '__activeItemChangedDetails', true);

        grid.$connector._getSameLevelPage = tryCatchWrapper(function (parentKey, currentCache, currentCacheItemIndex) {
          const currentParentKey = currentCache.parentItem ? grid.getItemId(currentCache.parentItem) : root;
          if (currentParentKey === parentKey) {
            // Level match found, return the page number.
            return Math.floor(currentCacheItemIndex / grid.pageSize);
          }
          const { parentCache, parentCacheIndex } = currentCache;
          if (!parentCache) {
            // There is no parent cache to match level
            return null;
          }
          // Traverse the tree upwards until a match is found or the end is reached
          return this._getSameLevelPage(parentKey, parentCache, parentCacheIndex);
        });

        grid.$connector.flushEnsureSubCache = tryCatchWrapper(function () {
          const pendingFetch = ensureSubCacheQueue.shift();
          if (pendingFetch) {
            dataProviderController.ensureFlatIndexHierarchyOriginal(pendingFetch.flatIndex);
            return true;
          }
          return false;
        });

        grid.$connector.debounceRootRequest = tryCatchWrapper(function (page) {
          const delay = grid._hasData ? rootRequestDelay : 0;

          rootRequestDebouncer = Debouncer.debounce(rootRequestDebouncer, timeOut.after(delay), () => {
            grid.$connector.fetchPage(
              (firstIndex, size) => grid.$server.setRequestedRange(firstIndex, size),
              page,
              root
            );
          });
        });

        grid.$connector.flushParentRequests = tryCatchWrapper(function () {
          const pendingFetches = [];

          parentRequestQueue.splice(0, parentRequestBatchMaxSize).forEach(({ parentKey, page }) => {
            grid.$connector.fetchPage(
              (firstIndex, size) => pendingFetches.push({ parentKey, firstIndex, size }),
              page,
              parentKey
            );
          });

          if (pendingFetches.length) {
            grid.$server.setParentRequestedRanges(pendingFetches);
          }
        });

        grid.$connector.debounceParentRequest = tryCatchWrapper(function (parentKey, page) {
          // Remove any pending requests for the same parentKey.
          parentRequestQueue = parentRequestQueue.filter((request) => request.parentKey !== parentKey);
          // Add the new request to the queue.
          parentRequestQueue.push({ parentKey, page });
          // Debounce the request to avoid sending multiple requests for the same parentKey.
          parentRequestDebouncer = Debouncer.debounce(parentRequestDebouncer, timeOut.after(parentRequestDelay), () => {
            while (parentRequestQueue.length) {
              grid.$connector.flushParentRequests();
            }
          });
        });

        grid.$connector.fetchPage = tryCatchWrapper(function (fetch, page, parentKey) {
          // Adjust the requested page to be within the valid range in case
          // the grid size has changed while fetchPage was debounced.
          if (parentKey === root) {
            page = Math.min(page, Math.floor((grid.size - 1) / grid.pageSize));
          }

          // Determine what to fetch based on scroll position and not only
          // what grid asked for
          const visibleRows = grid._getRenderedRows();
          let start = visibleRows.length > 0 ? visibleRows[0].index : 0;
          let end = visibleRows.length > 0 ? visibleRows[visibleRows.length - 1].index : 0;

          // The buffer size could be multiplied by some constant defined by the user,
          // if he needs to reduce the number of items sent to the Grid to improve performance
          // or to increase it to make Grid smoother when scrolling
          let buffer = end - start;
          let firstNeededIndex = Math.max(0, start - buffer);
          let lastNeededIndex = Math.min(end + buffer, grid._flatSize);

          let pageRange = [null, null];
          for (let idx = firstNeededIndex; idx <= lastNeededIndex; idx++) {
            const { cache, index } = dataProviderController.getFlatIndexContext(idx);
            // Try to match level by going up in hierarchy. The page range should include
            // pages that contain either of the following:
            //   - visible items of the current cache
            //   - same level parents of visible descendant items
            // If the parent items are not considered, Flow would remove the hidden parent
            // items from the current level cache. This can lead to an infinite loop when using
            // scrollToIndex feature.
            const sameLevelPage = grid.$connector._getSameLevelPage(parentKey, cache, index);
            if (sameLevelPage === null) {
              continue;
            }
            pageRange[0] = Math.min(pageRange[0] ?? sameLevelPage, sameLevelPage);
            pageRange[1] = Math.max(pageRange[1] ?? sameLevelPage, sameLevelPage);
          }

          // When the viewport doesn't contain the requested page or it doesn't contain any items from
          // the requested level at all, it means that the scroll position has changed while fetchPage
          // was debounced. For example, it can happen if the user scrolls the grid to the bottom and
          // then immediately back to the top. In this case, the request for the last page will be left
          // hanging. To avoid this, as a workaround, we reset the range to only include the requested page
          // to make sure all hanging requests are resolved. After that, the grid requests the first page
          // or whatever in the viewport again.
          if (pageRange.some((p) => p === null) || page < pageRange[0] || page > pageRange[1]) {
            pageRange = [page, page];
          }

          let lastRequestedRange = lastRequestedRanges[parentKey] || [-1, -1];
          if (lastRequestedRange[0] != pageRange[0] || lastRequestedRange[1] != pageRange[1]) {
            lastRequestedRanges[parentKey] = pageRange;
            let pageCount = pageRange[1] - pageRange[0] + 1;
            fetch(pageRange[0] * grid.pageSize, pageCount * grid.pageSize);
          }
        });

        grid.dataProvider = tryCatchWrapper(function (params, callback) {
          if (params.pageSize != grid.pageSize) {
            throw 'Invalid pageSize';
          }

          let page = params.page;

          if (params.parentItem) {
            let parentUniqueKey = grid.getItemId(params.parentItem);
            if (!treePageCallbacks[parentUniqueKey]) {
              treePageCallbacks[parentUniqueKey] = {};
            }

            const parentItemContext = dataProviderController.getItemContext(params.parentItem);
            if (cache[parentUniqueKey]?.[page] && parentItemContext.subCache) {
              // workaround: sometimes grid-element gives page index that overflows
              page = Math.min(page, Math.floor(cache[parentUniqueKey].size / grid.pageSize));

              // Ensure grid isn't in loading state when the callback executes
              ensureSubCacheQueue = [];
              // Resolve the callback from cache
              callback(cache[parentUniqueKey][page], cache[parentUniqueKey].size);
            } else {
              treePageCallbacks[parentUniqueKey][page] = callback;

              grid.$connector.debounceParentRequest(parentUniqueKey, page);
            }
          } else {
            // workaround: sometimes grid-element gives page index that overflows
            page = Math.min(page, Math.floor(grid.size / grid.pageSize));

            // size is controlled by the server (data communicator), so if the
            // size is zero, we know that there is no data to fetch.
            // This also prevents an empty grid getting stuck in a loading state.
            // The connector does not cache empty pages, so if the grid requests
            // data again, there would be no cache entry, causing a request to
            // the server. However, the data communicator will never respond,
            // as it assumes that the data is already cached.
            if (grid.size === 0) {
              callback([], 0);
              return;
            }

            if (cache[root]?.[page]) {
              callback(cache[root][page]);
            } else {
              rootPageCallbacks[page] = callback;

              grid.$connector.debounceRootRequest(page);
            }
          }
        });

        grid.$connector.setSorterDirections = tryCatchWrapper(function (directions) {
          sorterDirectionsSetFromServer = true;
          setTimeout(
            tryCatchWrapper(() => {
              try {
                const sorters = Array.from(grid.querySelectorAll('vaadin-grid-sorter'));

                // Sorters for hidden columns are removed from DOM but stored in the web component.
                // We need to ensure that all the sorters are reset when using `grid.sort(null)`.
                grid._sorters.forEach((sorter) => {
                  if (!sorters.includes(sorter)) {
                    sorters.push(sorter);
                  }
                });

                sorters.forEach((sorter) => {
                  sorter.direction = null;
                });

                // Apply directions in correct order, depending on configured multi-sort priority.
                // For the default "prepend" mode, directions need to be applied in reverse, in
                // order for the sort indicators to match the order on the server. For "append"
                // just keep the order passed from the server.
                if (grid.multiSortPriority !== 'append') {
                  directions = directions.reverse();
                }
                directions.forEach(({ column, direction }) => {
                  sorters.forEach((sorter) => {
                    if (sorter.getAttribute('path') === column) {
                      sorter.direction = direction;
                    }
                  });
                });
              } finally {
                sorterDirectionsSetFromServer = false;
              }
            })
          );
        });

        grid._updateItem = tryCatchWrapper(function (row, item) {
          Grid.prototype._updateItem.call(grid, row, item);

          // There might be inactive component renderers on hidden rows that still refer to the
          // same component instance as one of the renderers on a visible row. Making the
          // inactive/hidden renderer attach the component might steal it from a visible/active one.
          if (!row.hidden) {
            // make sure that component renderers are updated
            Array.from(row.children).forEach((cell) => {
              Array.from(cell?._content?.__templateInstance?.children || []).forEach((content) => {
                if (content._attachRenderedComponentIfAble) {
                  content._attachRenderedComponentIfAble();
                }
                // In hierarchy column of tree grid, the component renderer is inside its content,
                // this updates it renderer from innerContent
                Array.from(content?.children || []).forEach((innerContent) => {
                  if (innerContent._attachRenderedComponentIfAble) {
                    innerContent._attachRenderedComponentIfAble();
                  }
                });
              });
            });
          }
          // since no row can be selected when selection mode is NONE
          // if selectionMode is set to NONE, remove aria-selected attribute from the row
          if (selectionMode === validSelectionModes[1]) {
            // selectionMode === NONE
            row.removeAttribute('aria-selected');
            Array.from(row.children).forEach((cell) => cell.removeAttribute('aria-selected'));
          }
        });

        const itemExpandedChanged = tryCatchWrapper(function (item, expanded) {
          // method available only for the TreeGrid server-side component
          if (item == undefined || grid.$server.updateExpandedState == undefined) {
            return;
          }
          let parentKey = grid.getItemId(item);
          grid.$server.updateExpandedState(parentKey, expanded);
        });

        // Patch grid.expandItem and grid.collapseItem to have
        // itemExpandedChanged run when either happens.
        grid.expandItem = tryCatchWrapper(function (item) {
          itemExpandedChanged(item, true);
          Grid.prototype.expandItem.call(grid, item);
        });

        grid.collapseItem = tryCatchWrapper(function (item) {
          itemExpandedChanged(item, false);
          Grid.prototype.collapseItem.call(grid, item);
        });

        const itemsUpdated = function (items) {
          if (!items || !Array.isArray(items)) {
            throw 'Attempted to call itemsUpdated with an invalid value: ' + JSON.stringify(items);
          }
          let detailsOpenedItems = Array.from(grid.detailsOpenedItems);
          let updatedSelectedItem = false;
          for (let i = 0; i < items.length; ++i) {
            const item = items[i];
            if (!item) {
              continue;
            }
            if (item.detailsOpened) {
              if (grid._getItemIndexInArray(item, detailsOpenedItems) < 0) {
                detailsOpenedItems.push(item);
              }
            } else if (grid._getItemIndexInArray(item, detailsOpenedItems) >= 0) {
              detailsOpenedItems.splice(grid._getItemIndexInArray(item, detailsOpenedItems), 1);
            }
            if (selectedKeys[item.key]) {
              selectedKeys[item.key] = item;
              item.selected = true;
              updatedSelectedItem = true;
            }
          }
          grid.detailsOpenedItems = detailsOpenedItems;
          if (updatedSelectedItem) {
            // Replace the objects in the grid.selectedItems array without replacing the array
            // itself in order to avoid an unnecessary re-render of the grid.
            grid.selectedItems.splice(0, grid.selectedItems.length, ...Object.values(selectedKeys));
          }
        };

        /**
         * Updates the cache for the given page for grid or tree-grid.
         *
         * @param page index of the page to update
         * @param parentKey the key of the parent item for the page
         * @returns an array of the updated items for the page, or undefined if no items were cached for the page
         */
        const updateGridCache = function (page, parentKey) {
          let items;
          if ((parentKey || root) !== root) {
            items = cache[parentKey][page];
            const parentItem = createEmptyItemFromKey(parentKey);
            const parentItemContext = dataProviderController.getItemContext(parentItem);
            if (parentItemContext && parentItemContext.subCache) {
              const callbacksForParentKey = treePageCallbacks[parentKey];
              const callback = callbacksForParentKey && callbacksForParentKey[page];
              _updateGridCache(page, items, callback, parentItemContext.subCache);
            }
          } else {
            items = cache[root][page];
            _updateGridCache(page, items, rootPageCallbacks[page], dataProviderController.rootCache);
          }
          return items;
        };

        const _updateGridCache = function (page, items, callback, levelcache) {
          // Force update unless there's a callback waiting
          if (!callback) {
            let rangeStart = page * grid.pageSize;
            let rangeEnd = rangeStart + grid.pageSize;
            if (!items) {
              if (levelcache && levelcache.items) {
                for (let idx = rangeStart; idx < rangeEnd; idx++) {
                  delete levelcache.items[idx];
                }
              }
            } else {
              if (levelcache && levelcache.items) {
                for (let idx = rangeStart; idx < rangeEnd; idx++) {
                  if (levelcache.items[idx]) {
                    levelcache.items[idx] = items[idx - rangeStart];
                  }
                }
              }
            }
          }
        };

        /**
         * Updates all visible grid rows in DOM.
         */
        const updateAllGridRowsInDomBasedOnCache = function () {
          updateGridFlatSize();
          grid.__updateVisibleRows();
        };

        /**
         * Updates the <vaadin-grid>'s internal cache size and flat size.
         */
        const updateGridFlatSize = function () {
          dataProviderController.recalculateFlatSize();
          grid._flatSize = dataProviderController.flatSize;
        };

        /**
         * Update the given items in DOM if currently visible.
         *
         * @param array items the items to update in DOM
         */
        const updateGridItemsInDomBasedOnCache = function (items) {
          if (!items || !grid.$ || grid.$.items.childElementCount === 0) {
            return;
          }

          const itemKeys = items.map((item) => item.key);
          const indexes = grid
            ._getRenderedRows()
            .filter((row) => row._item && itemKeys.includes(row._item.key))
            .map((row) => row.index);
          if (indexes.length > 0) {
            grid.__updateVisibleRows(indexes[0], indexes[indexes.length - 1]);
          }
        };

        grid.$connector.set = tryCatchWrapper(function (index, items, parentKey) {
          if (index % grid.pageSize != 0) {
            throw 'Got new data to index ' + index + ' which is not aligned with the page size of ' + grid.pageSize;
          }
          let pkey = parentKey || root;

          const firstPage = index / grid.pageSize;
          const updatedPageCount = Math.ceil(items.length / grid.pageSize);

          for (let i = 0; i < updatedPageCount; i++) {
            let page = firstPage + i;
            let slice = items.slice(i * grid.pageSize, (i + 1) * grid.pageSize);
            if (!cache[pkey]) {
              cache[pkey] = {};
            }
            cache[pkey][page] = slice;

            grid.$connector.doSelection(slice.filter((item) => item.selected));
            grid.$connector.doDeselection(slice.filter((item) => !item.selected && selectedKeys[item.key]));

            const updatedItems = updateGridCache(page, pkey);
            if (updatedItems) {
              itemsUpdated(updatedItems);
              updateGridItemsInDomBasedOnCache(updatedItems);
            }
          }
        });

        const itemToCacheLocation = function (item) {
          let parent = item.parentUniqueKey || root;
          if (cache[parent]) {
            for (let page in cache[parent]) {
              for (let index in cache[parent][page]) {
                if (grid.getItemId(cache[parent][page][index]) === grid.getItemId(item)) {
                  return { page: page, index: index, parentKey: parent };
                }
              }
            }
          }
          return null;
        };

        /**
         * Updates the given items for a hierarchical grid.
         *
         * @param updatedItems the updated items array
         */
        grid.$connector.updateHierarchicalData = tryCatchWrapper(function (updatedItems) {
          let pagesToUpdate = [];
          // locate and update the items in cache
          // find pages that need updating
          for (let i = 0; i < updatedItems.length; i++) {
            let cacheLocation = itemToCacheLocation(updatedItems[i]);
            if (cacheLocation) {
              cache[cacheLocation.parentKey][cacheLocation.page][cacheLocation.index] = updatedItems[i];
              let key = cacheLocation.parentKey + ':' + cacheLocation.page;
              if (!pagesToUpdate[key]) {
                pagesToUpdate[key] = {
                  parentKey: cacheLocation.parentKey,
                  page: cacheLocation.page
                };
              }
            }
          }
          // IE11 doesn't work with the transpiled version of the forEach.
          let keys = Object.keys(pagesToUpdate);
          for (let i = 0; i < keys.length; i++) {
            let pageToUpdate = pagesToUpdate[keys[i]];
            const affectedUpdatedItems = updateGridCache(pageToUpdate.page, pageToUpdate.parentKey);
            if (affectedUpdatedItems) {
              itemsUpdated(affectedUpdatedItems);
              updateGridItemsInDomBasedOnCache(affectedUpdatedItems);
            }
          }
        });

        /**
         * Updates the given items for a non-hierarchical grid.
         *
         * @param updatedItems the updated items array
         */
        grid.$connector.updateFlatData = tryCatchWrapper(function (updatedItems) {
          // update (flat) caches
          for (let i = 0; i < updatedItems.length; i++) {
            let cacheLocation = itemToCacheLocation(updatedItems[i]);
            if (cacheLocation) {
              // update connector cache
              cache[cacheLocation.parentKey][cacheLocation.page][cacheLocation.index] = updatedItems[i];

              // update grid's cache
              const index = parseInt(cacheLocation.page) * grid.pageSize + parseInt(cacheLocation.index);
              const { rootCache } = dataProviderController;
              if (rootCache.items[index]) {
                rootCache.items[index] = updatedItems[i];
              }
            }
          }
          itemsUpdated(updatedItems);

          updateGridItemsInDomBasedOnCache(updatedItems);
        });

        grid.$connector.clearExpanded = tryCatchWrapper(function () {
          grid.expandedItems = [];
          ensureSubCacheQueue = [];
          parentRequestQueue = [];
        });

        grid.$connector.clear = tryCatchWrapper(function (index, length, parentKey) {
          let pkey = parentKey || root;
          if (!cache[pkey] || Object.keys(cache[pkey]).length === 0) {
            return;
          }
          if (index % grid.pageSize != 0) {
            throw (
              'Got cleared data for index ' + index + ' which is not aligned with the page size of ' + grid.pageSize
            );
          }

          let firstPage = Math.floor(index / grid.pageSize);
          let updatedPageCount = Math.ceil(length / grid.pageSize);

          for (let i = 0; i < updatedPageCount; i++) {
            let page = firstPage + i;
            let items = cache[pkey][page];
            grid.$connector.doDeselection(items.filter((item) => selectedKeys[item.key]));
            items.forEach((item) => grid.closeItemDetails(item));
            delete cache[pkey][page];
            const updatedItems = updateGridCache(page, parentKey);
            if (updatedItems) {
              itemsUpdated(updatedItems);
            }
            updateGridItemsInDomBasedOnCache(items);
          }
          let cacheToClear = dataProviderController.rootCache;
          if (parentKey) {
            const parentItem = createEmptyItemFromKey(pkey);
            const parentItemContext = dataProviderController.getItemContext(parentItem);
            cacheToClear = parentItemContext.subCache;
          }
          const endIndex = index + updatedPageCount * grid.pageSize;
          for (let itemIndex = index; itemIndex < endIndex; itemIndex++) {
            delete cacheToClear.items[itemIndex];
            cacheToClear.removeSubCache(itemIndex);
          }
          updateGridFlatSize();
        });

        grid.$connector.reset = tryCatchWrapper(function () {
          grid.size = 0;
          deleteObjectContents(cache);
          deleteObjectContents(dataProviderController.rootCache.items);
          deleteObjectContents(lastRequestedRanges);
          if (ensureSubCacheDebouncer) {
            ensureSubCacheDebouncer.cancel();
          }
          if (parentRequestDebouncer) {
            parentRequestDebouncer.cancel();
          }
          if (rootRequestDebouncer) {
            rootRequestDebouncer.cancel();
          }
          ensureSubCacheDebouncer = undefined;
          parentRequestDebouncer = undefined;
          ensureSubCacheQueue = [];
          parentRequestQueue = [];
          updateAllGridRowsInDomBasedOnCache();
        });

        const deleteObjectContents = (obj) => Object.keys(obj).forEach((key) => delete obj[key]);

        grid.$connector.updateSize = (newSize) => (grid.size = newSize);

        grid.$connector.updateUniqueItemIdPath = (path) => (grid.itemIdPath = path);

        grid.$connector.expandItems = tryCatchWrapper(function (items) {
          let newExpandedItems = Array.from(grid.expandedItems);
          items.filter((item) => !grid._isExpanded(item)).forEach((item) => newExpandedItems.push(item));
          grid.expandedItems = newExpandedItems;
        });

        grid.$connector.collapseItems = tryCatchWrapper(function (items) {
          let newExpandedItems = Array.from(grid.expandedItems);
          items.forEach((item) => {
            let index = grid._getItemIndexInArray(item, newExpandedItems);
            if (index >= 0) {
              newExpandedItems.splice(index, 1);
            }
          });
          grid.expandedItems = newExpandedItems;
          items.forEach((item) => grid.$connector.removeFromQueue(item));
        });

        grid.$connector.removeFromQueue = tryCatchWrapper(function (item) {
          let itemId = grid.getItemId(item);
          // The treePageCallbacks for the itemId are about to be discarded ->
          // Resolve the callbacks with an empty array to not leave grid in loading state
          Object.values(treePageCallbacks[itemId] || {}).forEach((callback) => callback([]));

          delete treePageCallbacks[itemId];
          ensureSubCacheQueue = ensureSubCacheQueue.filter((item) => item.itemkey !== itemId);
          parentRequestQueue = parentRequestQueue.filter((item) => item.parentKey !== itemId);
        });

        grid.$connector.confirmParent = tryCatchWrapper(function (id, parentKey, levelSize) {
          // Create connector cache if it doesn't exist
          if (!cache[parentKey]) {
            cache[parentKey] = {};
          }
          // Update connector cache size
          const hasSizeChanged = cache[parentKey].size !== levelSize;
          cache[parentKey].size = levelSize;
          if (levelSize === 0) {
            cache[parentKey][0] = [];
          }

          // If grid has outstanding requests for this parent, then resolve them
          // and let grid update the flat size and re-render.
          let outstandingRequests = Object.getOwnPropertyNames(treePageCallbacks[parentKey] || {});
          for (let i = 0; i < outstandingRequests.length; i++) {
            let page = outstandingRequests[i];

            let lastRequestedRange = lastRequestedRanges[parentKey] || [0, 0];

            const callback = treePageCallbacks[parentKey][page];
            if (
              (cache[parentKey] && cache[parentKey][page]) ||
              page < lastRequestedRange[0] ||
              page > lastRequestedRange[1]
            ) {
              delete treePageCallbacks[parentKey][page];
              let items = cache[parentKey][page] || new Array(levelSize);
              callback(items, levelSize);
            } else if (callback && levelSize === 0) {
              // The parent item has 0 child items => resolve the callback with an empty array
              delete treePageCallbacks[parentKey][page];
              callback([], levelSize);
            }
          }

          // If size has changed, and there are no outstanding requests, then
          // manually update the size of the grid cache and update the effective
          // size, effectively re-rendering the grid. This is necessary when
          // individual items are refreshed on the server, in which case there
          // is no loading request from the grid itself. In that case, if
          // children were added or removed, the grid will not be aware of it
          // unless we manually update the size.
          if (hasSizeChanged && outstandingRequests.length === 0) {
            const parentItem = createEmptyItemFromKey(parentKey);
            const parentItemContext = dataProviderController.getItemContext(parentItem);
            if (parentItemContext && parentItemContext.subCache) {
              parentItemContext.subCache.size = levelSize;
            }
            updateGridFlatSize();
          }

          // Let server know we're done
          grid.$server.confirmParentUpdate(id, parentKey);

          if (!grid.loading) {
            grid.__confirmParentUpdateDebouncer = Debouncer.debounce(
              grid.__confirmParentUpdateDebouncer,
              animationFrame,
              () => grid.__updateVisibleRows()
            );
          }
        });

        grid.$connector.confirm = tryCatchWrapper(function (id) {
          // We're done applying changes from this batch, resolve outstanding
          // callbacks
          let outstandingRequests = Object.getOwnPropertyNames(rootPageCallbacks);
          for (let i = 0; i < outstandingRequests.length; i++) {
            let page = outstandingRequests[i];
            let lastRequestedRange = lastRequestedRanges[root] || [0, 0];

            const lastAvailablePage = grid.size ? Math.ceil(grid.size / grid.pageSize) - 1 : 0;
            // It's possible that the lastRequestedRange includes a page that's beyond lastAvailablePage if the grid's size got reduced during an ongoing data request
            const lastRequestedRangeEnd = Math.min(lastRequestedRange[1], lastAvailablePage);
            // Resolve if we have data or if we don't expect to get data
            const callback = rootPageCallbacks[page];
            if (cache[root]?.[page] || page < lastRequestedRange[0] || +page > lastRequestedRangeEnd) {
              delete rootPageCallbacks[page];

              if (cache[root][page]) {
                // Cached data is available, resolve the callback
                callback(cache[root][page]);
              } else {
                // No cached data, resolve the callback with an empty array
                callback(new Array(grid.pageSize));
                // Request grid for content update
                grid.requestContentUpdate();
              }

            } else if (callback && grid.size === 0) {
              // The grid has 0 items => resolve the callback with an empty array
              delete rootPageCallbacks[page];
              callback([]);
            }
          }

          if (Object.keys(rootPageCallbacks).length) {
            // There are still unresolved callbacks waiting for data to the root level,
            // which means that the range grid requested items for was only partially filled.
            //
            // This can happen for example if you preload some items without knowing exactly
            // how many items the grid web component is going to request.
            //
            // Clear the last requested range for the root level to unblock
            // any possible data requests for the same range in fetchPage.
            delete lastRequestedRanges[root];
          }

          // Let server know we're done
          grid.$server.confirmUpdate(id);
        });

        grid.$connector.ensureHierarchy = tryCatchWrapper(function () {
          for (let parentKey in cache) {
            if (parentKey !== root) {
              delete cache[parentKey];
            }
          }
          deleteObjectContents(lastRequestedRanges);

          dataProviderController.rootCache.removeSubCaches();

          updateAllGridRowsInDomBasedOnCache();
        });

        grid.$connector.setSelectionMode = tryCatchWrapper(function (mode) {
          if ((typeof mode === 'string' || mode instanceof String) && validSelectionModes.indexOf(mode) >= 0) {
            selectionMode = mode;
            selectedKeys = {};
            grid.$connector.updateMultiSelectable();
          } else {
            throw 'Attempted to set an invalid selection mode';
          }
        });

        /*
         * Manage aria-multiselectable attribute depending on the selection mode.
         * see more: https://github.com/vaadin/web-components/issues/1536
         * or: https://www.w3.org/TR/wai-aria-1.1/#aria-multiselectable
         * For selection mode SINGLE, set the aria-multiselectable attribute to false
         */
        grid.$connector.updateMultiSelectable = tryCatchWrapper(function () {
          if (!grid.$) {
            return;
          }

          if (selectionMode === validSelectionModes[0]) {
            grid.$.table.setAttribute('aria-multiselectable', false);
            // For selection mode NONE, remove the aria-multiselectable attribute
          } else if (selectionMode === validSelectionModes[1]) {
            grid.$.table.removeAttribute('aria-multiselectable');
            // For selection mode MULTI, set aria-multiselectable to true
          } else {
            grid.$.table.setAttribute('aria-multiselectable', true);
          }
        });

        // Have the multi-selectable state updated on attach
        grid._createPropertyObserver('isAttached', () => grid.$connector.updateMultiSelectable());

        const singleTimeRenderer = (renderer) => {
          return (root) => {
            if (renderer) {
              renderer(root);
              renderer = null;
            }
          };
        };

        grid.$connector.setHeaderRenderer = tryCatchWrapper(function (column, options) {
          const { content, showSorter, sorterPath } = options;

          if (content === null) {
            column.headerRenderer = null;
            return;
          }

          column.headerRenderer = singleTimeRenderer((root) => {
            // Clear previous contents
            root.innerHTML = '';
            // Render sorter
            let contentRoot = root;
            if (showSorter) {
              const sorter = document.createElement('vaadin-grid-sorter');
              sorter.setAttribute('path', sorterPath);
              const ariaLabel = content instanceof Node ? content.textContent : content;
              if (ariaLabel) {
                sorter.setAttribute('aria-label', `Sort by ${ariaLabel}`);
              }
              root.appendChild(sorter);

              // Use sorter as content root
              contentRoot = sorter;
            }
            // Add content
            if (content instanceof Node) {
              contentRoot.appendChild(content);
            } else {
              contentRoot.textContent = content;
            }
          });
        });

        grid.__applySorters = () => {
          const sorters = grid._mapSorters();
          const sortersChanged = JSON.stringify(grid._previousSorters) !== JSON.stringify(sorters);

          // Update the _previousSorters in vaadin-grid-sort-mixin so that the __applySorters
          // method in the mixin will skip calling clearCache().
          //
          // In Flow Grid's case, we never want to clear the cache eagerly when the sorter elements
          // change due to one of the following reasons:
          //
          // 1. Sorted by user: The items in the new sort order need to be fetched from the server,
          // and we want to avoid a heavy re-render before the updated items have actually been fetched.
          //
          // 2. Sorted programmatically on the server: The items in the new sort order have already
          // been fetched and applied to the grid. The sorter element states are updated programmatically
          // to reflect the new sort order, but there's no need to re-render the grid rows.
          grid._previousSorters = sorters;

          // Call the original __applySorters method in vaadin-grid-sort-mixin
          Grid.prototype.__applySorters.call(grid);

          if (sortersChanged && !sorterDirectionsSetFromServer) {
            grid.$server.sortersChanged(sorters);
          }
        };

        grid.$connector.setFooterRenderer = tryCatchWrapper(function (column, options) {
          const { content } = options;

          if (content === null) {
            column.footerRenderer = null;
            return;
          }

          column.footerRenderer = singleTimeRenderer((root) => {
            // Clear previous contents
            root.innerHTML = '';
            // Add content
            if (content instanceof Node) {
              root.appendChild(content);
            } else {
              root.textContent = content;
            }
          });
        });

        grid.addEventListener(
          'vaadin-context-menu-before-open',
          tryCatchWrapper(function (e) {
            const { key, columnId } = e.detail;
            grid.$server.updateContextMenuTargetItem(key, columnId);
          })
        );

        grid.getContextMenuBeforeOpenDetail = tryCatchWrapper(function (event) {
          // For `contextmenu` events, we need to access the source event,
          // when using open on click we just use the click event itself
          const sourceEvent = event.detail.sourceEvent || event;
          const eventContext = grid.getEventContext(sourceEvent);
          const key = eventContext.item?.key || '';
          const columnId = eventContext.column?.id || '';
          return { key, columnId };
        });

        grid.preventContextMenu = tryCatchWrapper(function (event) {
            const isLeftClick = event.type === 'click';
            const { column } = grid.getEventContext(event);

            return isLeftClick && column instanceof GridFlowSelectionColumn;
        });

        grid.addEventListener(
          'click',
          tryCatchWrapper((e) => _fireClickEvent(e, 'item-click'))
        );
        grid.addEventListener(
          'dblclick',
          tryCatchWrapper((e) => _fireClickEvent(e, 'item-double-click'))
        );

        grid.addEventListener(
          'column-resize',
          tryCatchWrapper((e) => {
            const cols = grid._getColumnsInOrder().filter((col) => !col.hidden);

            cols.forEach((col) => {
              col.dispatchEvent(new CustomEvent('column-drag-resize'));
            });

            grid.dispatchEvent(
              new CustomEvent('column-drag-resize', {
                detail: {
                  resizedColumnKey: e.detail.resizedColumn._flowId
                }
              })
            );
          })
        );

        grid.addEventListener(
          'column-reorder',
          tryCatchWrapper((e) => {
            const columns = grid._columnTree
              .slice(0)
              .pop()
              .filter((c) => c._flowId)
              .sort((b, a) => b._order - a._order)
              .map((c) => c._flowId);

            grid.dispatchEvent(
              new CustomEvent('column-reorder-all-columns', {
                detail: { columns }
              })
            );
          })
        );

        grid.addEventListener(
          'cell-focus',
          tryCatchWrapper((e) => {
            const eventContext = grid.getEventContext(e);
            const expectedSectionValues = ['header', 'body', 'footer'];

            if (expectedSectionValues.indexOf(eventContext.section) === -1) {
              return;
            }

            grid.dispatchEvent(
              new CustomEvent('grid-cell-focus', {
                detail: {
                  itemKey: eventContext.item ? eventContext.item.key : null,

                  internalColumnId: eventContext.column ? eventContext.column._flowId : null,

                  section: eventContext.section
                }
              })
            );
          })
        );

        function _fireClickEvent(event, eventName) {
          // Click event was handled by the component inside grid, do nothing.
          if (event.defaultPrevented) {
            return;
          }

          const target = event.target;

          if (isFocusable(target) || target instanceof HTMLLabelElement) {
            return;
          }

          const eventContext = grid.getEventContext(event);
          const section = eventContext.section;

          if (eventContext.item && section !== 'details') {
            event.itemKey = eventContext.item.key;
            // if you have a details-renderer, getEventContext().column is undefined
            if (eventContext.column) {
              event.internalColumnId = eventContext.column._flowId;
            }
            grid.dispatchEvent(new CustomEvent(eventName, { detail: event }));
          }
        }

        grid.cellClassNameGenerator = tryCatchWrapper(function (column, rowData) {
          const style = rowData.item.style;
          if (!style) {
            return;
          }
          return (style.row || '') + ' ' + ((column && style[column._flowId]) || '');
        });

        grid.cellPartNameGenerator = tryCatchWrapper(function (column, rowData) {
          const part = rowData.item.part;
          if (!part) {
            return;
          }
          return (part.row || '') + ' ' + ((column && part[column._flowId]) || '');
        });

        grid.dropFilter = tryCatchWrapper((rowData) => rowData.item && !rowData.item.dropDisabled);

        grid.dragFilter = tryCatchWrapper((rowData) => rowData.item && !rowData.item.dragDisabled);

        grid.addEventListener(
          'grid-dragstart',
          tryCatchWrapper((e) => {
            if (grid._isSelected(e.detail.draggedItems[0])) {
              // Dragging selected (possibly multiple) items
              if (grid.__selectionDragData) {
                Object.keys(grid.__selectionDragData).forEach((type) => {
                  e.detail.setDragData(type, grid.__selectionDragData[type]);
                });
              } else {
                (grid.__dragDataTypes || []).forEach((type) => {
                  e.detail.setDragData(type, e.detail.draggedItems.map((item) => item.dragData[type]).join('\n'));
                });
              }

              if (grid.__selectionDraggedItemsCount > 1) {
                e.detail.setDraggedItemsCount(grid.__selectionDraggedItemsCount);
              }
            } else {
              // Dragging just one (non-selected) item
              (grid.__dragDataTypes || []).forEach((type) => {
                e.detail.setDragData(type, e.detail.draggedItems[0].dragData[type]);
              });
            }
          })
        );
      })(grid)
  };
})();
