import stripCssComments from 'strip-css-comments';

// Safari 15 - 16.3, polyfilled
const polyfilledSafari = CSSStyleSheet.toString().includes('document.createElement');

const createLinkReferences = (css, target) => {
  // Unresolved urls are written as '@import url(text);' or '@import "text";' to the css
  // media query can be present on @media tag or on @import directive after url
  // Note that with Vite production build there is no space between @import and "text"
  // [0] is the full match
  // [1] matches the media query
  // [2] matches the url
  // [3] matches the quote char surrounding in '@import "..."'
  // [4] matches the url in '@import "..."'
  // [5] matches media query on @import statement
  const importMatcher =
    /(?:@media\s(.+?))?(?:\s{)?\@import\s*(?:url\(\s*['"]?(.+?)['"]?\s*\)|(["'])((?:\\.|[^\\])*?)\3)([^;]*);(?:})?/g;

  // Only cleanup if comment exist
  if (/\/\*(.|[\r\n])*?\*\//gm.exec(css) != null) {
    // clean up comments
    css = stripCssComments(css);
  }

  var match;
  var styleCss = css;

  // For each external url import add a link reference
  while ((match = importMatcher.exec(css)) !== null) {
    styleCss = styleCss.replace(match[0], '');
    const link = document.createElement('link');
    link.rel = 'stylesheet';
    link.href = match[2] || match[4];
    const media = match[1] || match[5];
    if (media) {
      link.media = media;
    }
    // For target document append to head else append to target
    if (target === document) {
      document.head.appendChild(link);
    } else {
      target.appendChild(link);
    }
  }
  return styleCss;
};

const addAdoptedStyleSafariPolyfill = (sheet, target, first) => {
  if (first) {
    target.adoptedStyleSheets = [sheet, ...target.adoptedStyleSheets];
  } else {
    target.adoptedStyleSheets = [...target.adoptedStyleSheets, sheet];
  }
  return () => {
    target.adoptedStyleSheets = target.adoptedStyleSheets.filter((ss) => ss !== sheet);
  };
};

const addAdoptedStyle = (cssText, target, first) => {
  const sheet = new CSSStyleSheet();
  sheet.replaceSync(cssText);
  if (polyfilledSafari) {
    return addAdoptedStyleSafariPolyfill(sheet, target, first);
  }
  if (first) {
    target.adoptedStyleSheets.splice(0, 0, sheet);
  } else {
    target.adoptedStyleSheets.push(sheet);
  }
  return () => {
    target.adoptedStyleSheets.splice(target.adoptedStyleSheets.indexOf(sheet), 1);
  };
};

const addStyleTag = (cssText, referenceComment) => {
  const styleTag = document.createElement('style');
  styleTag.type = 'text/css';
  styleTag.textContent = cssText;

  let beforeThis = undefined;
  if (referenceComment) {
    const comments = Array.from(document.head.childNodes).filter(elem => elem.nodeType === Node.COMMENT_NODE);
    const container = comments.find(comment => comment.data.trim() === referenceComment);
    if (container) {
      beforeThis = container;
    }
  }
  document.head.insertBefore(styleTag, beforeThis);
  return () => {
    styleTag.remove();
  };
};

// target: Document | ShadowRoot
export const injectGlobalCss = (css, referenceComment, target, first) => {
  if (target === document) {
    const hash = getHash(css);
    if (window.Vaadin.theme.injectedGlobalCss.indexOf(hash) !== -1) {
      return;
    }
    window.Vaadin.theme.injectedGlobalCss.push(hash);
  }
  const cssText = createLinkReferences(css, target);

  // We avoid mixing style tags and adoptedStyleSheets to make override order clear
  if (target === document) {
    return addStyleTag(cssText, referenceComment);
  }

  return addAdoptedStyle(cssText, target, first);
};

window.Vaadin = window.Vaadin || {};
window.Vaadin.theme = window.Vaadin.theme || {};
window.Vaadin.theme.injectedGlobalCss = [];

/**
 * Calculate a 32 bit FNV-1a hash
 * Found here: https://gist.github.com/vaiorabbit/5657561
 * Ref.: http://isthe.com/chongo/tech/comp/fnv/
 *
 * @param {string} str the input value
 * @returns {string} 32 bit (as 8 byte hex string)
 */
function hashFnv32a(str) {
  /*jshint bitwise:false */
  let i,
    l,
    hval = 0x811c9dc5;

  for (i = 0, l = str.length; i < l; i++) {
    hval ^= str.charCodeAt(i);
    hval += (hval << 1) + (hval << 4) + (hval << 7) + (hval << 8) + (hval << 24);
  }

  // Convert to 8 digit hex string
  return ('0000000' + (hval >>> 0).toString(16)).substr(-8);
}

/**
 * Calculate a 64 bit hash for the given input.
 * Double hash is used to significantly lower the collision probability.
 *
 * @param {string} input value to get hash for
 * @returns {string} 64 bit (as 16 byte hex string)
 */
function getHash(input) {
  let h1 = hashFnv32a(input); // returns 32 bit (as 8 byte hex string)
  return h1 + hashFnv32a(h1 + input);
}
