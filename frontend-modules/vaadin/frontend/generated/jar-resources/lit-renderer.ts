/* eslint-disable no-restricted-syntax */
/* eslint-disable max-params */
import { html, render } from 'lit';

type RenderRoot = HTMLElement & { __litRenderer?: Renderer; _$litPart$?: any };

type ItemModel = { item: any; index: number };

type Renderer = ((root: RenderRoot, rendererOwner: HTMLElement, model: ItemModel) => void) & { __rendererId?: string };

type Component = HTMLElement & { [key: string]: Renderer | undefined };

const _window = window as any;
_window.Vaadin = _window.Vaadin || {};

/**
 * Assigns the component a renderer function which uses Lit to render
 * the given template expression inside the render root element.
 *
 * @param component The host component to which the renderer runction is to be set
 * @param rendererName The name of the renderer function
 * @param templateExpression The content of the template literal passed to Lit for rendering.
 * @param returnChannel A channel to the server.
 * Calling it will end up invoking a handler in the server-side LitRenderer.
 * @param clientCallables A list of function names that can be called from within the template literal.
 * @param propertyNamespace LitRenderer-specific namespace for properties.
 * Needed to avoid property name collisions between renderers.
 */
_window.Vaadin.setLitRenderer = (
  component: Component,
  rendererName: string,
  templateExpression: string,
  returnChannel: (name: string, itemKey: string, args: any[]) => void,
  clientCallables: string[],
  propertyNamespace: string,
) => {
  // Dynamically created function that renders the templateExpression
  // inside the given root element using Lit
  const renderFunction = Function(`
    "use strict";

    const [render, html, returnChannel] = arguments;

    return (root, model, itemKey) => {
      const { item, index } = model;
      ${clientCallables
        .map((clientCallable) => {
          // Map all the client-callables as inline functions so they can be accessed from the template literal
          return `
          const ${clientCallable} = (...args) => {
            if (itemKey !== undefined) {
              returnChannel('${clientCallable}', itemKey, args[0] instanceof Event ? [] : [...args]);
            }
          }`;
        })
        .join('')}

      render(html\`${templateExpression}\`, root)
    }
  `)(render, html, returnChannel);

  const renderer: Renderer = (root, _, model) => {
    const { item } = model;
    // Clean up the root element of any existing content
    // (and Lit's _$litPart$ property) from other renderers
    // TODO: Remove once https://github.com/vaadin/web-components/issues/2235 is done
    if (root.__litRenderer !== renderer) {
      root.innerHTML = '';
      delete root._$litPart$;
      root.__litRenderer = renderer;
    }

    // Map a new item that only includes the properties defined by
    // this specific LitRenderer instance. The renderer instance specific
    // "propertyNamespace" prefix is stripped from the property name at this point:
    //
    // item: { key: "2", lr_3_lastName: "Tyler"}
    // ->
    // mappedItem: { lastName: "Tyler" }
    const mappedItem: { [key: string]: any } = {};
    for (const key in item) {
      if (key.startsWith(propertyNamespace)) {
        mappedItem[key.replace(propertyNamespace, '')] = item[key];
      }
    }

    renderFunction(root, { ...model, item: mappedItem }, item.key);
  };

  renderer.__rendererId = propertyNamespace;
  component[rendererName] = renderer;
};

/**
 * Removes the renderer function with the given name from the component
 * if the propertyNamespace matches the renderer's id.
 *
 * @param component The host component whose renderer function is to be removed
 * @param rendererName The name of the renderer function
 * @param rendererId The rendererId of the function to be removed
 */
_window.Vaadin.unsetLitRenderer = (component: Component, rendererName: string, rendererId: string) => {
  // The check for __rendererId property is necessary since the renderer function
  // may get overridden by another renderer, for example, by one coming from
  // vaadin-template-renderer. We don't want LitRenderer registration cleanup to
  // unintentionally remove the new renderer.
  if (component[rendererName]?.__rendererId === rendererId) {
    component[rendererName] = undefined;
  }
};
