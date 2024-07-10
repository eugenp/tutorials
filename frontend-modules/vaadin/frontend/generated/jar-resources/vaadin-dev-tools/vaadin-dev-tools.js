import"construct-style-sheets-polyfill";import{css as y,svg as B,LitElement as T,html as c,render as Se,nothing as pt}from"lit";import{property as m,state as w,customElement as k,query as Q}from"lit/decorators.js";import{classMap as ot}from"lit/directives/class-map.js";import{literal as ee,html as ut}from"lit/static-html.js";const vt="modulepreload",mt=function(s){return"/"+s},Oe={},d=function(e,t,o){if(!t||t.length===0)return e();const i=document.getElementsByTagName("link");return Promise.all(t.map(n=>{if(n=mt(n),n in Oe)return;Oe[n]=!0;const r=n.endsWith(".css"),a=r?'[rel="stylesheet"]':"";if(!!o)for(let g=i.length-1;g>=0;g--){const x=i[g];if(x.href===n&&(!r||x.rel==="stylesheet"))return}else if(document.querySelector(`link[href="${n}"]${a}`))return;const p=document.createElement("link");if(p.rel=r?"stylesheet":vt,r||(p.as="script",p.crossOrigin=""),p.href=n,document.head.appendChild(p),r)return new Promise((g,x)=>{p.addEventListener("load",g),p.addEventListener("error",()=>x(new Error(`Unable to preload CSS for ${n}`)))})})).then(()=>e()).catch(n=>{const r=new Event("vite:preloadError",{cancelable:!0});if(r.payload=n,window.dispatchEvent(r),!r.defaultPrevented)throw n})};function l(s,e,t,o){var i=arguments.length,n=i<3?e:o===null?o=Object.getOwnPropertyDescriptor(e,t):o,r;if(typeof Reflect=="object"&&typeof Reflect.decorate=="function")n=Reflect.decorate(s,e,t,o);else for(var a=s.length-1;a>=0;a--)(r=s[a])&&(n=(i<3?r(n):i>3?r(e,t,n):r(e,t))||n);return i>3&&n&&Object.defineProperty(e,t,n),n}const Te=1e3,Ae=(s,e)=>{const t=Array.from(s.querySelectorAll(e.join(", "))),o=Array.from(s.querySelectorAll("*")).filter(i=>i.shadowRoot).flatMap(i=>Ae(i.shadowRoot,e));return[...t,...o]};let Le=!1;const Y=(s,e)=>{Le||(window.addEventListener("message",i=>{i.data==="validate-license"&&window.location.reload()},!1),Le=!0);const t=s._overlayElement;if(t){if(t.shadowRoot){const i=t.shadowRoot.querySelector("slot:not([name])");if(i&&i.assignedElements().length>0){Y(i.assignedElements()[0],e);return}}Y(t,e);return}const o=e.messageHtml?e.messageHtml:`${e.message} <p>Component: ${e.product.name} ${e.product.version}</p>`.replace(/https:([^ ]*)/g,"<a href='https:$1'>https:$1</a>");s.isConnected&&(s.outerHTML=`<no-license style="display:flex;align-items:center;text-align:center;justify-content:center;"><div>${o}</div></no-license>`)},K={},Pe={},F={},st={},$=s=>`${s.name}_${s.version}`,Ve=s=>{const{cvdlName:e,version:t}=s.constructor,o={name:e,version:t},i=s.tagName.toLowerCase();K[e]=K[e]??[],K[e].push(i);const n=F[$(o)];n&&setTimeout(()=>Y(s,n),Te),F[$(o)]||st[$(o)]||Pe[$(o)]||(Pe[$(o)]=!0,window.Vaadin.devTools.checkLicense(o))},gt=s=>{st[$(s)]=!0,console.debug("License check ok for",s)},it=s=>{const e=s.product.name;F[$(s.product)]=s,console.error("License check failed for",e);const t=K[e];(t==null?void 0:t.length)>0&&Ae(document,t).forEach(o=>{setTimeout(()=>Y(o,F[$(s.product)]),Te)})},ft=s=>{const e=s.message,t=s.product.name;s.messageHtml=`No license found. <a target=_blank onclick="javascript:window.open(this.href);return false;" href="${e}">Go here to start a trial or retrieve your license.</a>`,F[$(s.product)]=s,console.error("No license found when checking",t);const o=K[t];(o==null?void 0:o.length)>0&&Ae(document,o).forEach(i=>{setTimeout(()=>Y(i,F[$(s.product)]),Te)})},yt=s=>s.command==="license-check-ok"?(gt(s.data),!0):s.command==="license-check-failed"?(it(s.data),!0):s.command==="license-check-nokey"?(ft(s.data),!0):!1,bt=()=>{window.Vaadin.devTools.createdCvdlElements.forEach(s=>{Ve(s)}),window.Vaadin.devTools.createdCvdlElements={push:s=>{Ve(s)}}};function wt(s){var t;const e=[];for(;s&&s.parentNode;){const o=we(s);if(o.nodeId!==-1){if((t=o.element)!=null&&t.tagName.startsWith("FLOW-CONTAINER-"))break;e.push(o)}s=s.parentElement?s.parentElement:s.parentNode.host}return e.reverse()}function we(s){const e=window.Vaadin;if(e&&e.Flow){const{clients:t}=e.Flow,o=Object.keys(t);for(const i of o){const n=t[i];if(n.getNodeId){const r=n.getNodeId(s);if(r>=0)return{nodeId:r,uiId:n.getUIId(),element:s}}}}return{nodeId:-1,uiId:-1,element:void 0}}function _t(s,e){if(s.contains(e))return!0;let t=e;const o=e.ownerDocument;for(;t&&t!==o&&t!==s;)t=t.parentNode||(t instanceof ShadowRoot?t.host:null);return t===s}var u;(function(s){s.ACTIVE="active",s.INACTIVE="inactive",s.UNAVAILABLE="unavailable",s.ERROR="error"})(u||(u={}));class ie{constructor(){this.status=u.UNAVAILABLE}onHandshake(){}onConnectionError(e){}onStatusChange(e){}setActive(e){!e&&this.status===u.ACTIVE?this.setStatus(u.INACTIVE):e&&this.status===u.INACTIVE&&this.setStatus(u.ACTIVE)}setStatus(e){this.status!==e&&(this.status=e,this.onStatusChange(e))}}ie.HEARTBEAT_INTERVAL=18e4;class xt extends ie{constructor(e){super(),this.webSocket=new WebSocket(e),this.webSocket.onmessage=t=>this.handleMessage(t),this.webSocket.onerror=t=>this.handleError(t),this.webSocket.onclose=t=>{this.status!==u.ERROR&&this.setStatus(u.UNAVAILABLE),this.webSocket=void 0},setInterval(()=>{this.webSocket&&self.status!==u.ERROR&&this.status!==u.UNAVAILABLE&&this.webSocket.send("")},ie.HEARTBEAT_INTERVAL)}onReload(){}handleMessage(e){let t;try{t=JSON.parse(e.data)}catch(o){this.handleError(`[${o.name}: ${o.message}`);return}t.command==="hello"?(this.setStatus(u.ACTIVE),this.onHandshake()):t.command==="reload"?this.status===u.ACTIVE&&this.onReload():this.handleError(`Unknown message from the livereload server: ${e}`)}handleError(e){console.error(e),this.setStatus(u.ERROR),e instanceof Event&&this.webSocket?this.onConnectionError(`Error in WebSocket connection to ${this.webSocket.url}`):this.onConnectionError(e)}}const nt=y`
  .popup {
    width: auto;
    position: fixed;
    background-color: var(--dev-tools-background-color-active-blurred);
    color: var(--dev-tools-text-color-primary);
    padding: 0.1875rem 0.75rem 0.1875rem 1rem;
    background-clip: padding-box;
    border-radius: var(--dev-tools-border-radius);
    overflow: hidden;
    margin: 0.5rem;
    width: 30rem;
    max-width: calc(100% - 1rem);
    max-height: calc(100vh - 1rem);
    flex-shrink: 1;
    background-color: var(--dev-tools-background-color-active);
    color: var(--dev-tools-text-color);
    transition: var(--dev-tools-transition-duration);
    transform-origin: bottom right;
    display: flex;
    flex-direction: column;
    box-shadow: var(--dev-tools-box-shadow);
    outline: none;
  }
`,Et=(s,e)=>{const t=s[e];return t?typeof t=="function"?t():Promise.resolve(t):new Promise((o,i)=>{(typeof queueMicrotask=="function"?queueMicrotask:setTimeout)(i.bind(null,new Error("Unknown variable dynamic import: "+e)))})};var b;(function(s){s.text="text",s.checkbox="checkbox",s.range="range",s.color="color"})(b||(b={}));const N={lumoSize:["--lumo-size-xs","--lumo-size-s","--lumo-size-m","--lumo-size-l","--lumo-size-xl"],lumoSpace:["--lumo-space-xs","--lumo-space-s","--lumo-space-m","--lumo-space-l","--lumo-space-xl"],lumoBorderRadius:["0","--lumo-border-radius-m","--lumo-border-radius-l"],lumoFontSize:["--lumo-font-size-xxs","--lumo-font-size-xs","--lumo-font-size-s","--lumo-font-size-m","--lumo-font-size-l","--lumo-font-size-xl","--lumo-font-size-xxl","--lumo-font-size-xxxl"],lumoTextColor:["--lumo-header-text-color","--lumo-body-text-color","--lumo-secondary-text-color","--lumo-tertiary-text-color","--lumo-disabled-text-color","--lumo-primary-text-color","--lumo-error-text-color","--lumo-success-text-color"],basicBorderSize:["0px","1px","2px","3px"]},kt=Object.freeze(Object.defineProperty({__proto__:null,presets:N},Symbol.toStringTag,{value:"Module"})),O={textColor:{propertyName:"color",displayName:"Text color",editorType:b.color,presets:N.lumoTextColor},fontSize:{propertyName:"font-size",displayName:"Font size",editorType:b.range,presets:N.lumoFontSize,icon:"font"},fontWeight:{propertyName:"font-weight",displayName:"Bold",editorType:b.checkbox,checkedValue:"bold"},fontStyle:{propertyName:"font-style",displayName:"Italic",editorType:b.checkbox,checkedValue:"italic"}},I={backgroundColor:{propertyName:"background-color",displayName:"Background color",editorType:b.color},borderColor:{propertyName:"border-color",displayName:"Border color",editorType:b.color},borderWidth:{propertyName:"border-width",displayName:"Border width",editorType:b.range,presets:N.basicBorderSize,icon:"square"},borderRadius:{propertyName:"border-radius",displayName:"Border radius",editorType:b.range,presets:N.lumoBorderRadius,icon:"square"},padding:{propertyName:"padding",displayName:"Padding",editorType:b.range,presets:N.lumoSpace,icon:"square"},gap:{propertyName:"gap",displayName:"Spacing",editorType:b.range,presets:N.lumoSpace,icon:"square"}},Ct={height:{propertyName:"height",displayName:"Size",editorType:b.range,presets:N.lumoSize,icon:"square"},paddingInline:{propertyName:"padding-inline",displayName:"Padding",editorType:b.range,presets:N.lumoSpace,icon:"square"}},_e={iconColor:{propertyName:"color",displayName:"Icon color",editorType:b.color,presets:N.lumoTextColor},iconSize:{propertyName:"font-size",displayName:"Icon size",editorType:b.range,presets:N.lumoFontSize,icon:"font"}},St=[I.backgroundColor,I.borderColor,I.borderWidth,I.borderRadius,I.padding],Tt=[O.textColor,O.fontSize,O.fontWeight,O.fontStyle],At=[_e.iconColor,_e.iconSize],Rt=Object.freeze(Object.defineProperty({__proto__:null,fieldProperties:Ct,iconProperties:_e,shapeProperties:I,standardIconProperties:At,standardShapeProperties:St,standardTextProperties:Tt,textProperties:O},Symbol.toStringTag,{value:"Module"}));function rt(s){const e=s.charAt(0).toUpperCase()+s.slice(1);return{tagName:s,displayName:e,elements:[{selector:s,displayName:"Element",properties:[I.backgroundColor,I.borderColor,I.borderWidth,I.borderRadius,I.padding,O.textColor,O.fontSize,O.fontWeight,O.fontStyle]}]}}const It=Object.freeze(Object.defineProperty({__proto__:null,createGenericMetadata:rt},Symbol.toStringTag,{value:"Module"})),Nt=s=>Et(Object.assign({"./components/defaults.ts":()=>d(()=>Promise.resolve().then(()=>Rt),void 0),"./components/generic.ts":()=>d(()=>Promise.resolve().then(()=>It),void 0),"./components/presets.ts":()=>d(()=>Promise.resolve().then(()=>kt),void 0),"./components/vaadin-accordion-heading.ts":()=>d(()=>import("./assets/vaadin-accordion-heading-c0acdd6d.js"),[]),"./components/vaadin-accordion-panel.ts":()=>d(()=>import("./assets/vaadin-accordion-panel-616e55d6.js"),[]),"./components/vaadin-accordion.ts":()=>d(()=>import("./assets/vaadin-accordion-eed3b794.js"),[]),"./components/vaadin-app-layout.ts":()=>d(()=>import("./assets/vaadin-app-layout-e56de2e9.js"),[]),"./components/vaadin-avatar.ts":()=>d(()=>import("./assets/vaadin-avatar-7599297d.js"),[]),"./components/vaadin-big-decimal-field.ts":()=>d(()=>import("./assets/vaadin-big-decimal-field-e51def24.js"),["assets/vaadin-big-decimal-field-e51def24.js","assets/vaadin-text-field-0b3db014.js","assets/vaadin-button-2511ad84.js"]),"./components/vaadin-board-row.ts":()=>d(()=>import("./assets/vaadin-board-row-c70d0c55.js"),[]),"./components/vaadin-board.ts":()=>d(()=>import("./assets/vaadin-board-828ebdea.js"),[]),"./components/vaadin-button.ts":()=>d(()=>import("./assets/vaadin-button-2511ad84.js"),[]),"./components/vaadin-chart.ts":()=>d(()=>import("./assets/vaadin-chart-5192dc15.js"),[]),"./components/vaadin-checkbox-group.ts":()=>d(()=>import("./assets/vaadin-checkbox-group-a7c65bf2.js"),["assets/vaadin-checkbox-group-a7c65bf2.js","assets/vaadin-text-field-0b3db014.js","assets/vaadin-checkbox-4e68df64.js"]),"./components/vaadin-checkbox.ts":()=>d(()=>import("./assets/vaadin-checkbox-4e68df64.js"),[]),"./components/vaadin-combo-box.ts":()=>d(()=>import("./assets/vaadin-combo-box-96451ddd.js"),["assets/vaadin-combo-box-96451ddd.js","assets/vaadin-text-field-0b3db014.js"]),"./components/vaadin-confirm-dialog.ts":()=>d(()=>import("./assets/vaadin-confirm-dialog-4d718829.js"),["assets/vaadin-confirm-dialog-4d718829.js","assets/vaadin-button-2511ad84.js"]),"./components/vaadin-cookie-consent.ts":()=>d(()=>import("./assets/vaadin-cookie-consent-46c09f8b.js"),[]),"./components/vaadin-crud.ts":()=>d(()=>import("./assets/vaadin-crud-8d161a22.js"),[]),"./components/vaadin-custom-field.ts":()=>d(()=>import("./assets/vaadin-custom-field-42c85b9e.js"),["assets/vaadin-custom-field-42c85b9e.js","assets/vaadin-text-field-0b3db014.js"]),"./components/vaadin-date-picker.ts":()=>d(()=>import("./assets/vaadin-date-picker-f2001167.js"),["assets/vaadin-date-picker-f2001167.js","assets/vaadin-text-field-0b3db014.js"]),"./components/vaadin-date-time-picker.ts":()=>d(()=>import("./assets/vaadin-date-time-picker-c8c047a7.js"),["assets/vaadin-date-time-picker-c8c047a7.js","assets/vaadin-text-field-0b3db014.js"]),"./components/vaadin-details-summary.ts":()=>d(()=>import("./assets/vaadin-details-summary-351a1448.js"),[]),"./components/vaadin-details.ts":()=>d(()=>import("./assets/vaadin-details-bf336660.js"),[]),"./components/vaadin-dialog.ts":()=>d(()=>import("./assets/vaadin-dialog-53253a08.js"),[]),"./components/vaadin-email-field.ts":()=>d(()=>import("./assets/vaadin-email-field-d7a35f04.js"),["assets/vaadin-email-field-d7a35f04.js","assets/vaadin-text-field-0b3db014.js","assets/vaadin-button-2511ad84.js"]),"./components/vaadin-form-layout.ts":()=>d(()=>import("./assets/vaadin-form-layout-47744b1d.js"),[]),"./components/vaadin-grid-pro.ts":()=>d(()=>import("./assets/vaadin-grid-pro-ff415555.js"),["assets/vaadin-grid-pro-ff415555.js","assets/vaadin-checkbox-4e68df64.js","assets/vaadin-grid-0a4791c2.js","assets/vaadin-text-field-0b3db014.js"]),"./components/vaadin-grid.ts":()=>d(()=>import("./assets/vaadin-grid-0a4791c2.js"),["assets/vaadin-grid-0a4791c2.js","assets/vaadin-checkbox-4e68df64.js"]),"./components/vaadin-horizontal-layout.ts":()=>d(()=>import("./assets/vaadin-horizontal-layout-3193943f.js"),[]),"./components/vaadin-icon.ts":()=>d(()=>import("./assets/vaadin-icon-601f36ed.js"),[]),"./components/vaadin-integer-field.ts":()=>d(()=>import("./assets/vaadin-integer-field-85078932.js"),["assets/vaadin-integer-field-85078932.js","assets/vaadin-text-field-0b3db014.js","assets/vaadin-button-2511ad84.js"]),"./components/vaadin-list-box.ts":()=>d(()=>import("./assets/vaadin-list-box-d7a8433b.js"),[]),"./components/vaadin-login-form.ts":()=>d(()=>import("./assets/vaadin-login-form-638996c6.js"),["assets/vaadin-login-form-638996c6.js","assets/vaadin-text-field-0b3db014.js","assets/vaadin-button-2511ad84.js"]),"./components/vaadin-login-overlay.ts":()=>d(()=>import("./assets/vaadin-login-overlay-f8a5db8a.js"),["assets/vaadin-login-overlay-f8a5db8a.js","assets/vaadin-text-field-0b3db014.js","assets/vaadin-button-2511ad84.js"]),"./components/vaadin-map.ts":()=>d(()=>import("./assets/vaadin-map-d40a0116.js"),[]),"./components/vaadin-menu-bar.ts":()=>d(()=>import("./assets/vaadin-menu-bar-3f5ab096.js"),[]),"./components/vaadin-message-input.ts":()=>d(()=>import("./assets/vaadin-message-input-996ac37c.js"),["assets/vaadin-message-input-996ac37c.js","assets/vaadin-text-field-0b3db014.js"]),"./components/vaadin-message-list.ts":()=>d(()=>import("./assets/vaadin-message-list-70a435ba.js"),[]),"./components/vaadin-multi-select-combo-box.ts":()=>d(()=>import("./assets/vaadin-multi-select-combo-box-a3373557.js"),["assets/vaadin-multi-select-combo-box-a3373557.js","assets/vaadin-text-field-0b3db014.js"]),"./components/vaadin-notification.ts":()=>d(()=>import("./assets/vaadin-notification-bd6eb776.js"),[]),"./components/vaadin-number-field.ts":()=>d(()=>import("./assets/vaadin-number-field-cb3ee8b2.js"),["assets/vaadin-number-field-cb3ee8b2.js","assets/vaadin-text-field-0b3db014.js","assets/vaadin-button-2511ad84.js"]),"./components/vaadin-password-field.ts":()=>d(()=>import("./assets/vaadin-password-field-d289cb18.js"),["assets/vaadin-password-field-d289cb18.js","assets/vaadin-text-field-0b3db014.js","assets/vaadin-button-2511ad84.js"]),"./components/vaadin-progress-bar.ts":()=>d(()=>import("./assets/vaadin-progress-bar-309ecf1f.js"),[]),"./components/vaadin-radio-group.ts":()=>d(()=>import("./assets/vaadin-radio-group-88b5afd8.js"),["assets/vaadin-radio-group-88b5afd8.js","assets/vaadin-text-field-0b3db014.js"]),"./components/vaadin-rich-text-editor.ts":()=>d(()=>import("./assets/vaadin-rich-text-editor-8cd892f2.js"),[]),"./components/vaadin-scroller.ts":()=>d(()=>import("./assets/vaadin-scroller-35e68818.js"),[]),"./components/vaadin-select.ts":()=>d(()=>import("./assets/vaadin-select-df6e9947.js"),["assets/vaadin-select-df6e9947.js","assets/vaadin-text-field-0b3db014.js"]),"./components/vaadin-side-nav-item.ts":()=>d(()=>import("./assets/vaadin-side-nav-item-34918f92.js"),[]),"./components/vaadin-side-nav.ts":()=>d(()=>import("./assets/vaadin-side-nav-ba80d91d.js"),[]),"./components/vaadin-split-layout.ts":()=>d(()=>import("./assets/vaadin-split-layout-80c92131.js"),[]),"./components/vaadin-spreadsheet.ts":()=>d(()=>import("./assets/vaadin-spreadsheet-59d8c5ef.js"),[]),"./components/vaadin-tab.ts":()=>d(()=>import("./assets/vaadin-tab-aaf32809.js"),[]),"./components/vaadin-tabs.ts":()=>d(()=>import("./assets/vaadin-tabs-d9a5e24e.js"),[]),"./components/vaadin-tabsheet.ts":()=>d(()=>import("./assets/vaadin-tabsheet-dd99ed9a.js"),[]),"./components/vaadin-text-area.ts":()=>d(()=>import("./assets/vaadin-text-area-83627ebc.js"),["assets/vaadin-text-area-83627ebc.js","assets/vaadin-text-field-0b3db014.js","assets/vaadin-button-2511ad84.js"]),"./components/vaadin-text-field.ts":()=>d(()=>import("./assets/vaadin-text-field-0b3db014.js"),[]),"./components/vaadin-time-picker.ts":()=>d(()=>import("./assets/vaadin-time-picker-715ec415.js"),["assets/vaadin-time-picker-715ec415.js","assets/vaadin-text-field-0b3db014.js"]),"./components/vaadin-upload.ts":()=>d(()=>import("./assets/vaadin-upload-d3c162ed.js"),["assets/vaadin-upload-d3c162ed.js","assets/vaadin-button-2511ad84.js"]),"./components/vaadin-vertical-layout.ts":()=>d(()=>import("./assets/vaadin-vertical-layout-ad4174c4.js"),[]),"./components/vaadin-virtual-list.ts":()=>d(()=>import("./assets/vaadin-virtual-list-96896203.js"),[])}),`./components/${s}.ts`);class $t{constructor(e=Nt){this.loader=e,this.metadata={}}async getMetadata(e){var i;const t=(i=e.element)==null?void 0:i.localName;if(!t)return null;if(!t.startsWith("vaadin-"))return rt(t);let o=this.metadata[t];if(o)return o;try{o=(await this.loader(t)).default,this.metadata[t]=o}catch{console.warn(`Failed to load metadata for component: ${t}`)}return o||null}}const Ot=new $t,se={crosshair:B`<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round">
   <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
   <path d="M4 8v-2a2 2 0 0 1 2 -2h2"></path>
   <path d="M4 16v2a2 2 0 0 0 2 2h2"></path>
   <path d="M16 4h2a2 2 0 0 1 2 2v2"></path>
   <path d="M16 20h2a2 2 0 0 0 2 -2v-2"></path>
   <path d="M9 12l6 0"></path>
   <path d="M12 9l0 6"></path>
</svg>`,square:B`<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" fill="currentColor" stroke-linecap="round" stroke-linejoin="round">
   <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
   <path d="M3 3m0 2a2 2 0 0 1 2 -2h14a2 2 0 0 1 2 2v14a2 2 0 0 1 -2 2h-14a2 2 0 0 1 -2 -2z"></path>
</svg>`,font:B`<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round">
   <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
   <path d="M4 20l3 0"></path>
   <path d="M14 20l7 0"></path>
   <path d="M6.9 15l6.9 0"></path>
   <path d="M10.2 6.3l5.8 13.7"></path>
   <path d="M5 20l6 -16l2 0l7 16"></path>
</svg>`,undo:B`<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round">
   <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
   <path d="M9 13l-4 -4l4 -4m-4 4h11a4 4 0 0 1 0 8h-1"></path>
</svg>`,redo:B`<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round">
   <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
   <path d="M15 13l4 -4l-4 -4m4 4h-11a4 4 0 0 0 0 8h1"></path>
</svg>`,cross:B`<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" stroke-width="3" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round">
   <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
   <path d="M18 6l-12 12"></path>
   <path d="M6 6l12 12"></path>
</svg>`};var j;(function(s){s.disabled="disabled",s.enabled="enabled",s.missing_theme="missing_theme"})(j||(j={}));var _;(function(s){s.local="local",s.global="global"})(_||(_={}));function ve(s,e){return`${s}|${e}`}class V{constructor(e){this._properties={},this._metadata=e}get metadata(){return this._metadata}get properties(){return Object.values(this._properties)}getPropertyValue(e,t){return this._properties[ve(e,t)]||null}updatePropertyValue(e,t,o,i){if(!o){delete this._properties[ve(e,t)];return}let n=this.getPropertyValue(e,t);n?(n.value=o,n.modified=i||!1):(n={elementSelector:e,propertyName:t,value:o,modified:i||!1},this._properties[ve(e,t)]=n)}addPropertyValues(e){e.forEach(t=>{this.updatePropertyValue(t.elementSelector,t.propertyName,t.value,t.modified)})}getPropertyValuesForElement(e){return this.properties.filter(t=>t.elementSelector===e)}static combine(...e){if(e.length<2)throw new Error("Must provide at least two themes");const t=new V(e[0].metadata);return e.forEach(o=>t.addPropertyValues(o.properties)),t}static fromServerRules(e,t,o){const i=new V(e);return e.elements.forEach(n=>{const r=G(n,t),a=o.find(h=>h.selector===r.replace(/ > /g,">"));a&&n.properties.forEach(h=>{const p=a.properties[h.propertyName];p&&i.updatePropertyValue(n.selector,h.propertyName,p,!0)})}),i}}function G(s,e){const t=s.selector;if(e.themeScope===_.global)return t;if(!e.localClassName)throw new Error("Can not build local scoped selector without instance class name");const o=t.match(/^[\w\d-_]+/),i=o&&o[0];if(!i)throw new Error(`Selector does not start with a tag name: ${t}`);return`${i}.${e.localClassName}${t.substring(i.length,t.length)}`}function Lt(s,e,t,o){const i=G(s,e),n={[t]:o};return t==="border-width"&&(parseInt(o)>0?n["border-style"]="solid":n["border-style"]=""),{selector:i,properties:n}}function Pt(s){const e=Object.entries(s.properties).map(([t,o])=>`${t}: ${o};`).join(" ");return`${s.selector} { ${e} }`}let te,Me="";function Re(s){te||(te=new CSSStyleSheet,document.adoptedStyleSheets=[...document.adoptedStyleSheets,te]),Me+=s.cssText,te.replaceSync(Me)}const at=y`
  .editor-row {
    display: flex;
    align-items: baseline;
    padding: var(--theme-editor-section-horizontal-padding);
    gap: 10px;
  }

  .editor-row > .label {
    flex: 0 0 auto;
    width: 120px;
  }

  .editor-row > .editor {
    flex: 1 1 0;
  }
`,De="__vaadin-theme-editor-measure-element",ze=/((::before)|(::after))$/,Ue=/::part\(([\w\d_-]+)\)$/;Re(y`
  .__vaadin-theme-editor-measure-element {
    position: absolute;
    top: 0;
    left: 0;
    visibility: hidden;
  }
`);async function Vt(s){const e=new V(s),t=document.createElement(s.tagName);t.classList.add(De),document.body.append(t),s.setupElement&&await s.setupElement(t);const o={themeScope:_.local,localClassName:De};try{s.elements.forEach(i=>{He(t,i,o,!0);let n=G(i,o);const r=n.match(ze);n=n.replace(ze,"");const a=n.match(Ue),h=n.replace(Ue,"");let p=document.querySelector(h);if(p&&a){const z=`[part~="${a[1]}"]`;p=p.shadowRoot.querySelector(z)}if(!p)return;p.style.transition="none";const g=r?r[1]:null,x=getComputedStyle(p,g);i.properties.forEach(D=>{const z=x.getPropertyValue(D.propertyName)||D.defaultValue||"";e.updatePropertyValue(i.selector,D.propertyName,z)}),He(t,i,o,!1)})}finally{try{s.cleanupElement&&await s.cleanupElement(t)}finally{t.remove()}}return e}function He(s,e,t,o){if(e.stateAttribute){if(e.stateElementSelector){const i=G({...e,selector:e.stateElementSelector},t);s=document.querySelector(i)}s&&(o?s.setAttribute(e.stateAttribute,""):s.removeAttribute(e.stateAttribute))}}function Be(s){return s.trim()}function Mt(s){const e=s.element;if(!e)return null;const t=e.querySelector("label");if(t&&t.textContent)return Be(t.textContent);const o=e.textContent;return o?Be(o):null}class Dt{constructor(){this._localClassNameMap=new Map}get stylesheet(){return this.ensureStylesheet(),this._stylesheet}add(e){this.ensureStylesheet(),this._stylesheet.replaceSync(e)}clear(){this.ensureStylesheet(),this._stylesheet.replaceSync("")}previewLocalClassName(e,t){if(!e)return;const o=this._localClassNameMap.get(e);o&&(e.classList.remove(o),e.overlayClass=null),t?(e.classList.add(t),e.overlayClass=t,this._localClassNameMap.set(e,t)):this._localClassNameMap.delete(e)}ensureStylesheet(){this._stylesheet||(this._stylesheet=new CSSStyleSheet,this._stylesheet.replaceSync(""),document.adoptedStyleSheets=[...document.adoptedStyleSheets,this._stylesheet])}}const U=new Dt;var R;(function(s){s.response="themeEditorResponse",s.loadComponentMetadata="themeEditorComponentMetadata",s.setLocalClassName="themeEditorLocalClassName",s.setCssRules="themeEditorRules",s.loadRules="themeEditorLoadRules",s.history="themeEditorHistory",s.openCss="themeEditorOpenCss",s.markAsUsed="themeEditorMarkAsUsed"})(R||(R={}));var xe;(function(s){s.ok="ok",s.error="error"})(xe||(xe={}));class zt{constructor(e){this.pendingRequests={},this.requestCounter=0,this.wrappedConnection=e;const t=this.wrappedConnection.onMessage;this.wrappedConnection.onMessage=o=>{o.command===R.response?this.handleResponse(o.data):t.call(this.wrappedConnection,o)}}sendRequest(e,t){const o=(this.requestCounter++).toString(),i=t.uiId??this.getGlobalUiId();return new Promise((n,r)=>{this.wrappedConnection.send(e,{...t,requestId:o,uiId:i}),this.pendingRequests[o]={resolve:n,reject:r}})}handleResponse(e){const t=this.pendingRequests[e.requestId];if(!t){console.warn("Received response for unknown request");return}delete this.pendingRequests[e.requestId],e.code===xe.ok?t.resolve(e):t.reject(e)}loadComponentMetadata(e){return this.sendRequest(R.loadComponentMetadata,{nodeId:e.nodeId})}setLocalClassName(e,t){return this.sendRequest(R.setLocalClassName,{nodeId:e.nodeId,className:t})}setCssRules(e){return this.sendRequest(R.setCssRules,{rules:e})}loadRules(e){return this.sendRequest(R.loadRules,{selectors:e})}markAsUsed(){return this.sendRequest(R.markAsUsed,{})}undo(e){return this.sendRequest(R.history,{undo:e})}redo(e){return this.sendRequest(R.history,{redo:e})}openCss(e){return this.sendRequest(R.openCss,{selector:e})}getGlobalUiId(){if(this.globalUiId===void 0){const e=window.Vaadin;if(e&&e.Flow){const{clients:t}=e.Flow,o=Object.keys(t);for(const i of o){const n=t[i];if(n.getNodeId){this.globalUiId=n.getUIId();break}}}}return this.globalUiId??-1}}const E={index:-1,entries:[]};class Ut{constructor(e){this.api=e}get allowUndo(){return E.index>=0}get allowRedo(){return E.index<E.entries.length-1}get allowedActions(){return{allowUndo:this.allowUndo,allowRedo:this.allowRedo}}push(e,t,o){const i={requestId:e,execute:t,rollback:o};if(E.index++,E.entries=E.entries.slice(0,E.index),E.entries.push(i),t)try{t()}catch(n){console.error("Execute history entry failed",n)}return this.allowedActions}async undo(){if(!this.allowUndo)return this.allowedActions;const e=E.entries[E.index];E.index--;try{await this.api.undo(e.requestId),e.rollback&&e.rollback()}catch(t){console.error("Undo failed",t)}return this.allowedActions}async redo(){if(!this.allowRedo)return this.allowedActions;E.index++;const e=E.entries[E.index];try{await this.api.redo(e.requestId),e.execute&&e.execute()}catch(t){console.error("Redo failed",t)}return this.allowedActions}static clear(){E.entries=[],E.index=-1}}class Ht extends CustomEvent{constructor(e,t,o){super("theme-property-value-change",{bubbles:!0,composed:!0,detail:{element:e,property:t,value:o}})}}class A extends T{constructor(){super(...arguments),this.value=""}static get styles(){return[at,y`
        :host {
          display: block;
        }

        .editor-row .label .modified {
          display: inline-block;
          width: 6px;
          height: 6px;
          background: orange;
          border-radius: 3px;
          margin-left: 3px;
        }
      `]}update(e){super.update(e),(e.has("propertyMetadata")||e.has("theme"))&&this.updateValueFromTheme()}render(){var e;return c`
      <div class="editor-row">
        <div class="label">
          ${this.propertyMetadata.displayName}
          ${(e=this.propertyValue)!=null&&e.modified?c`<span class="modified"></span>`:null}
        </div>
        <div class="editor">${this.renderEditor()}</div>
      </div>
    `}updateValueFromTheme(){var e;this.propertyValue=this.theme.getPropertyValue(this.elementMetadata.selector,this.propertyMetadata.propertyName),this.value=((e=this.propertyValue)==null?void 0:e.value)||""}dispatchChange(e){this.dispatchEvent(new Ht(this.elementMetadata,this.propertyMetadata,e))}}l([m({})],A.prototype,"elementMetadata",void 0);l([m({})],A.prototype,"propertyMetadata",void 0);l([m({})],A.prototype,"theme",void 0);l([w()],A.prototype,"propertyValue",void 0);l([w()],A.prototype,"value",void 0);class ne{get values(){return this._values}get rawValues(){return this._rawValues}constructor(e){if(this._values=[],this._rawValues={},e){const t=e.propertyName,o=e.presets??[];this._values=(o||[]).map(n=>n.startsWith("--")?`var(${n})`:n);const i=document.createElement("div");i.style.borderStyle="solid",i.style.visibility="hidden",document.body.append(i);try{this._values.forEach(n=>{i.style.setProperty(t,n);const r=getComputedStyle(i);this._rawValues[n]=r.getPropertyValue(t).trim()})}finally{i.remove()}}}tryMapToRawValue(e){return this._rawValues[e]??e}tryMapToPreset(e){return this.findPreset(e)??e}findPreset(e){const t=e&&e.trim();return this.values.find(o=>this._rawValues[o]===t)}}class qe extends CustomEvent{constructor(e){super("change",{detail:{value:e}})}}let re=class extends T{constructor(){super(...arguments),this.value="",this.showClearButton=!1}static get styles(){return y`
      :host {
        display: inline-block;
        width: 100%;
        position: relative;
      }

      input {
        width: 100%;
        box-sizing: border-box;
        padding: 0.25rem 0.375rem;
        color: inherit;
        background: rgba(0, 0, 0, 0.2);
        border-radius: 0.25rem;
        border: none;
      }

      button {
        display: none;
        position: absolute;
        right: 4px;
        top: 4px;
        padding: 0;
        line-height: 0;
        border: none;
        background: none;
        color: var(--dev-tools-text-color);
      }

      button svg {
        width: 16px;
        height: 16px;
      }

      button:not(:disabled):hover {
        color: var(--dev-tools-text-color-emphasis);
      }

      :host(.show-clear-button) input {
        padding-right: 20px;
      }

      :host(.show-clear-button) button {
        display: block;
      }
    `}update(e){super.update(e),e.has("showClearButton")&&(this.showClearButton?this.classList.add("show-clear-button"):this.classList.remove("show-clear-button"))}render(){return c`
      <input class="input" .value=${this.value} @change=${this.handleInputChange} />
      <button @click=${this.handleClearClick}>${se.cross}</button>
    `}handleInputChange(e){const t=e.target;this.dispatchEvent(new qe(t.value))}handleClearClick(){this.dispatchEvent(new qe(""))}};l([m({})],re.prototype,"value",void 0);l([m({})],re.prototype,"showClearButton",void 0);re=l([k("vaadin-dev-tools-theme-text-input")],re);class Bt extends CustomEvent{constructor(e){super("class-name-change",{detail:{value:e}})}}let J=class extends T{constructor(){super(...arguments),this.editedClassName="",this.invalid=!1}static get styles(){return[at,y`
        .editor-row {
          padding-top: 0;
        }

        .editor-row .editor .error {
          display: inline-block;
          color: var(--dev-tools-red-color);
          margin-top: 4px;
        }
      `]}update(e){super.update(e),e.has("className")&&(this.editedClassName=this.className,this.invalid=!1)}render(){return c` <div class="editor-row local-class-name">
      <div class="label">CSS class name</div>
      <div class="editor">
        <vaadin-dev-tools-theme-text-input
          type="text"
          .value=${this.editedClassName}
          @change=${this.handleInputChange}
        ></vaadin-dev-tools-theme-text-input>
        ${this.invalid?c`<br /><span class="error">Please enter a valid CSS class name</span>`:null}
      </div>
    </div>`}handleInputChange(e){this.editedClassName=e.detail.value;const t=/^-?[_a-zA-Z]+[_a-zA-Z0-9-]*$/;this.invalid=!this.editedClassName.match(t),!this.invalid&&this.editedClassName!==this.className&&this.dispatchEvent(new Bt(this.editedClassName))}};l([m({})],J.prototype,"className",void 0);l([w()],J.prototype,"editedClassName",void 0);l([w()],J.prototype,"invalid",void 0);J=l([k("vaadin-dev-tools-theme-class-name-editor")],J);class qt extends CustomEvent{constructor(e){super("scope-change",{detail:{value:e}})}}Re(y`
  vaadin-select-overlay[theme~='vaadin-dev-tools-theme-scope-selector'] {
    --lumo-primary-color-50pct: rgba(255, 255, 255, 0.5);
    z-index: 100000 !important;
  }

  vaadin-select-overlay[theme~='vaadin-dev-tools-theme-scope-selector']::part(overlay) {
    background: #333;
  }

  vaadin-select-overlay[theme~='vaadin-dev-tools-theme-scope-selector'] vaadin-item {
    color: rgba(255, 255, 255, 0.8);
  }

  vaadin-select-overlay[theme~='vaadin-dev-tools-theme-scope-selector'] vaadin-item::part(content) {
    font-size: 13px;
  }

  vaadin-select-overlay[theme~='vaadin-dev-tools-theme-scope-selector'] vaadin-item .title {
    color: rgba(255, 255, 255, 0.95);
    font-weight: bold;
  }

  vaadin-select-overlay[theme~='vaadin-dev-tools-theme-scope-selector'] vaadin-item::part(checkmark) {
    margin: 6px;
  }

  vaadin-select-overlay[theme~='vaadin-dev-tools-theme-scope-selector'] vaadin-item::part(checkmark)::before {
    color: rgba(255, 255, 255, 0.95);
  }

  vaadin-select-overlay[theme~='vaadin-dev-tools-theme-scope-selector'] vaadin-item:hover {
    background: rgba(255, 255, 255, 0.1);
  }
`);let X=class extends T{constructor(){super(...arguments),this.value=_.local}static get styles(){return y`
      vaadin-select {
        --lumo-primary-color-50pct: rgba(255, 255, 255, 0.5);
        width: 100px;
      }

      vaadin-select::part(input-field) {
        background: rgba(0, 0, 0, 0.2);
      }

      vaadin-select vaadin-select-value-button,
      vaadin-select::part(toggle-button) {
        color: var(--dev-tools-text-color);
      }

      vaadin-select:hover vaadin-select-value-button,
      vaadin-select:hover::part(toggle-button) {
        color: var(--dev-tools-text-color-emphasis);
      }

      vaadin-select vaadin-select-item {
        font-size: 13px;
      }
    `}update(e){var t;super.update(e),e.has("metadata")&&((t=this.select)==null||t.requestContentUpdate())}render(){return c` <vaadin-select
      theme="small vaadin-dev-tools-theme-scope-selector"
      .value=${this.value}
      .renderer=${this.selectRenderer.bind(this)}
      @value-changed=${this.handleValueChange}
    ></vaadin-select>`}selectRenderer(e){var i;const t=((i=this.metadata)==null?void 0:i.displayName)||"Component",o=`${t}s`;Se(c`
        <vaadin-list-box>
          <vaadin-item value=${_.local} label="Local">
            <span class="title">Local</span>
            <br />
            <span>Edit styles for this ${t}</span>
          </vaadin-item>
          <vaadin-item value=${_.global} label="Global">
            <span class="title">Global</span>
            <br />
            <span>Edit styles for all ${o}</span>
          </vaadin-item>
        </vaadin-list-box>
      `,e)}handleValueChange(e){const t=e.detail.value;t!==this.value&&this.dispatchEvent(new qt(t))}};l([m({})],X.prototype,"value",void 0);l([m({})],X.prototype,"metadata",void 0);l([Q("vaadin-select")],X.prototype,"select",void 0);X=l([k("vaadin-dev-tools-theme-scope-selector")],X);let Fe=class extends A{static get styles(){return[A.styles,y`
        .editor-row {
          align-items: center;
        }
      `]}handleInputChange(e){const o=e.target.checked?this.propertyMetadata.checkedValue:"";this.dispatchChange(o||"")}renderEditor(){const e=this.value===this.propertyMetadata.checkedValue;return c` <input type="checkbox" .checked=${e} @change=${this.handleInputChange} /> `}};Fe=l([k("vaadin-dev-tools-theme-checkbox-property-editor")],Fe);let je=class extends A{handleInputChange(e){this.dispatchChange(e.detail.value)}renderEditor(){var e;return c`
      <vaadin-dev-tools-theme-text-input
        .value=${this.value}
        .showClearButton=${((e=this.propertyValue)==null?void 0:e.modified)||!1}
        @change=${this.handleInputChange}
      ></vaadin-dev-tools-theme-text-input>
    `}};je=l([k("vaadin-dev-tools-theme-text-property-editor")],je);let ae=class extends A{constructor(){super(...arguments),this.selectedPresetIndex=-1,this.presets=new ne}static get styles(){return[A.styles,y`
        :host {
          --preset-count: 3;
          --slider-bg: #fff;
          --slider-border: #333;
        }

        .editor-row {
          align-items: center;
        }

        .editor-row > .editor {
          display: flex;
          align-items: center;
          gap: 1rem;
        }

        .editor-row .input {
          flex: 0 0 auto;
          width: 80px;
        }

        .slider-wrapper {
          flex: 1 1 0;
          display: flex;
          align-items: center;
          gap: 0.5rem;
        }

        .icon {
          width: 20px;
          height: 20px;
          color: #aaa;
        }

        .icon.prefix > svg {
          transform: scale(0.75);
        }

        .slider {
          flex: 1 1 0;
          -webkit-appearance: none;
          background: linear-gradient(to right, #666, #666 2px, transparent 2px);
          background-size: calc((100% - 13px) / (var(--preset-count) - 1)) 8px;
          background-position: 5px 50%;
          background-repeat: repeat-x;
        }

        .slider::-webkit-slider-runnable-track {
          width: 100%;
          box-sizing: border-box;
          height: 16px;
          background-image: linear-gradient(#666, #666);
          background-size: calc(100% - 12px) 2px;
          background-repeat: no-repeat;
          background-position: 6px 50%;
        }

        .slider::-moz-range-track {
          width: 100%;
          box-sizing: border-box;
          height: 16px;
          background-image: linear-gradient(#666, #666);
          background-size: calc(100% - 12px) 2px;
          background-repeat: no-repeat;
          background-position: 6px 50%;
        }

        .slider::-webkit-slider-thumb {
          -webkit-appearance: none;
          height: 16px;
          width: 16px;
          border: 2px solid var(--slider-border);
          border-radius: 50%;
          background: var(--slider-bg);
          cursor: pointer;
        }

        .slider::-moz-range-thumb {
          height: 16px;
          width: 16px;
          border: 2px solid var(--slider-border);
          border-radius: 50%;
          background: var(--slider-bg);
          cursor: pointer;
        }

        .custom-value {
          opacity: 0.5;
        }

        .custom-value:hover,
        .custom-value:focus-within {
          opacity: 1;
        }

        .custom-value:not(:hover, :focus-within) {
          --slider-bg: #333;
          --slider-border: #666;
        }
      `]}update(e){e.has("propertyMetadata")&&(this.presets=new ne(this.propertyMetadata)),super.update(e)}renderEditor(){var o;const e={"slider-wrapper":!0,"custom-value":this.selectedPresetIndex<0},t=this.presets.values.length;return c`
      <div class=${ot(e)}>
        ${null}
        <input
          type="range"
          class="slider"
          style="--preset-count: ${t}"
          step="1"
          min="0"
          .max=${(t-1).toString()}
          .value=${this.selectedPresetIndex}
          @input=${this.handleSliderInput}
          @change=${this.handleSliderChange}
        />
        ${null}
      </div>
      <vaadin-dev-tools-theme-text-input
        class="input"
        .value=${this.value}
        .showClearButton=${((o=this.propertyValue)==null?void 0:o.modified)||!1}
        @change=${this.handleValueChange}
      ></vaadin-dev-tools-theme-text-input>
    `}handleSliderInput(e){const t=e.target,o=parseInt(t.value),i=this.presets.values[o];this.selectedPresetIndex=o,this.value=this.presets.rawValues[i]}handleSliderChange(){this.dispatchChange(this.value)}handleValueChange(e){this.value=e.detail.value,this.updateSliderValue(),this.dispatchChange(this.value)}dispatchChange(e){const t=this.presets.tryMapToPreset(e);super.dispatchChange(t)}updateValueFromTheme(){var e;super.updateValueFromTheme(),this.value=this.presets.tryMapToRawValue(((e=this.propertyValue)==null?void 0:e.value)||""),this.updateSliderValue()}updateSliderValue(){const e=this.presets.findPreset(this.value);this.selectedPresetIndex=e?this.presets.values.indexOf(e):-1}};l([w()],ae.prototype,"selectedPresetIndex",void 0);l([w()],ae.prototype,"presets",void 0);ae=l([k("vaadin-dev-tools-theme-range-property-editor")],ae);const W=(s,e=0,t=1)=>s>t?t:s<e?e:s,C=(s,e=0,t=Math.pow(10,e))=>Math.round(t*s)/t,lt=({h:s,s:e,v:t,a:o})=>{const i=(200-e)*t/100;return{h:C(s),s:C(i>0&&i<200?e*t/100/(i<=100?i:200-i)*100:0),l:C(i/2),a:C(o,2)}},Ee=s=>{const{h:e,s:t,l:o}=lt(s);return`hsl(${e}, ${t}%, ${o}%)`},me=s=>{const{h:e,s:t,l:o,a:i}=lt(s);return`hsla(${e}, ${t}%, ${o}%, ${i})`},Ft=({h:s,s:e,v:t,a:o})=>{s=s/360*6,e=e/100,t=t/100;const i=Math.floor(s),n=t*(1-e),r=t*(1-(s-i)*e),a=t*(1-(1-s+i)*e),h=i%6;return{r:C([t,r,n,n,a,t][h]*255),g:C([a,t,t,r,n,n][h]*255),b:C([n,n,a,t,t,r][h]*255),a:C(o,2)}},jt=s=>{const{r:e,g:t,b:o,a:i}=Ft(s);return`rgba(${e}, ${t}, ${o}, ${i})`},Gt=s=>{const t=/rgba?\(?\s*(-?\d*\.?\d+)(%)?[,\s]+(-?\d*\.?\d+)(%)?[,\s]+(-?\d*\.?\d+)(%)?,?\s*[/\s]*(-?\d*\.?\d+)?(%)?\s*\)?/i.exec(s);return t?Wt({r:Number(t[1])/(t[2]?100/255:1),g:Number(t[3])/(t[4]?100/255:1),b:Number(t[5])/(t[6]?100/255:1),a:t[7]===void 0?1:Number(t[7])/(t[8]?100:1)}):{h:0,s:0,v:0,a:1}},Wt=({r:s,g:e,b:t,a:o})=>{const i=Math.max(s,e,t),n=i-Math.min(s,e,t),r=n?i===s?(e-t)/n:i===e?2+(t-s)/n:4+(s-e)/n:0;return{h:C(60*(r<0?r+6:r)),s:C(i?n/i*100:0),v:C(i/255*100),a:o}},Kt=(s,e)=>{if(s===e)return!0;for(const t in s)if(s[t]!==e[t])return!1;return!0},Yt=(s,e)=>s.replace(/\s/g,"")===e.replace(/\s/g,""),Ge={},dt=s=>{let e=Ge[s];return e||(e=document.createElement("template"),e.innerHTML=s,Ge[s]=e),e},Ie=(s,e,t)=>{s.dispatchEvent(new CustomEvent(e,{bubbles:!0,detail:t}))};let q=!1;const ke=s=>"touches"in s,Jt=s=>q&&!ke(s)?!1:(q||(q=ke(s)),!0),We=(s,e)=>{const t=ke(e)?e.touches[0]:e,o=s.el.getBoundingClientRect();Ie(s.el,"move",s.getMove({x:W((t.pageX-(o.left+window.pageXOffset))/o.width),y:W((t.pageY-(o.top+window.pageYOffset))/o.height)}))},Xt=(s,e)=>{const t=e.keyCode;t>40||s.xy&&t<37||t<33||(e.preventDefault(),Ie(s.el,"move",s.getMove({x:t===39?.01:t===37?-.01:t===34?.05:t===33?-.05:t===35?1:t===36?-1:0,y:t===40?.01:t===38?-.01:0},!0)))};class Ne{constructor(e,t,o,i){const n=dt(`<div role="slider" tabindex="0" part="${t}" ${o}><div part="${t}-pointer"></div></div>`);e.appendChild(n.content.cloneNode(!0));const r=e.querySelector(`[part=${t}]`);r.addEventListener("mousedown",this),r.addEventListener("touchstart",this),r.addEventListener("keydown",this),this.el=r,this.xy=i,this.nodes=[r.firstChild,r]}set dragging(e){const t=e?document.addEventListener:document.removeEventListener;t(q?"touchmove":"mousemove",this),t(q?"touchend":"mouseup",this)}handleEvent(e){switch(e.type){case"mousedown":case"touchstart":if(e.preventDefault(),!Jt(e)||!q&&e.button!=0)return;this.el.focus(),We(this,e),this.dragging=!0;break;case"mousemove":case"touchmove":e.preventDefault(),We(this,e);break;case"mouseup":case"touchend":this.dragging=!1;break;case"keydown":Xt(this,e);break}}style(e){e.forEach((t,o)=>{for(const i in t)this.nodes[o].style.setProperty(i,t[i])})}}class Zt extends Ne{constructor(e){super(e,"hue",'aria-label="Hue" aria-valuemin="0" aria-valuemax="360"',!1)}update({h:e}){this.h=e,this.style([{left:`${e/360*100}%`,color:Ee({h:e,s:100,v:100,a:1})}]),this.el.setAttribute("aria-valuenow",`${C(e)}`)}getMove(e,t){return{h:t?W(this.h+e.x*360,0,360):360*e.x}}}class Qt extends Ne{constructor(e){super(e,"saturation",'aria-label="Color"',!0)}update(e){this.hsva=e,this.style([{top:`${100-e.v}%`,left:`${e.s}%`,color:Ee(e)},{"background-color":Ee({h:e.h,s:100,v:100,a:1})}]),this.el.setAttribute("aria-valuetext",`Saturation ${C(e.s)}%, Brightness ${C(e.v)}%`)}getMove(e,t){return{s:t?W(this.hsva.s+e.x*100,0,100):e.x*100,v:t?W(this.hsva.v-e.y*100,0,100):Math.round(100-e.y*100)}}}const eo=':host{display:flex;flex-direction:column;position:relative;width:200px;height:200px;user-select:none;-webkit-user-select:none;cursor:default}:host([hidden]){display:none!important}[role=slider]{position:relative;touch-action:none;user-select:none;-webkit-user-select:none;outline:0}[role=slider]:last-child{border-radius:0 0 8px 8px}[part$=pointer]{position:absolute;z-index:1;box-sizing:border-box;width:28px;height:28px;display:flex;place-content:center center;transform:translate(-50%,-50%);background-color:#fff;border:2px solid #fff;border-radius:50%;box-shadow:0 2px 4px rgba(0,0,0,.2)}[part$=pointer]::after{content:"";width:100%;height:100%;border-radius:inherit;background-color:currentColor}[role=slider]:focus [part$=pointer]{transform:translate(-50%,-50%) scale(1.1)}',to="[part=hue]{flex:0 0 24px;background:linear-gradient(to right,red 0,#ff0 17%,#0f0 33%,#0ff 50%,#00f 67%,#f0f 83%,red 100%)}[part=hue-pointer]{top:50%;z-index:2}",oo="[part=saturation]{flex-grow:1;border-color:transparent;border-bottom:12px solid #000;border-radius:8px 8px 0 0;background-image:linear-gradient(to top,#000,transparent),linear-gradient(to right,#fff,rgba(255,255,255,0));box-shadow:inset 0 0 0 1px rgba(0,0,0,.05)}[part=saturation-pointer]{z-index:3}",oe=Symbol("same"),ge=Symbol("color"),Ke=Symbol("hsva"),fe=Symbol("update"),Ye=Symbol("parts"),le=Symbol("css"),de=Symbol("sliders");let so=class extends HTMLElement{static get observedAttributes(){return["color"]}get[le](){return[eo,to,oo]}get[de](){return[Qt,Zt]}get color(){return this[ge]}set color(e){if(!this[oe](e)){const t=this.colorModel.toHsva(e);this[fe](t),this[ge]=e}}constructor(){super();const e=dt(`<style>${this[le].join("")}</style>`),t=this.attachShadow({mode:"open"});t.appendChild(e.content.cloneNode(!0)),t.addEventListener("move",this),this[Ye]=this[de].map(o=>new o(t))}connectedCallback(){if(this.hasOwnProperty("color")){const e=this.color;delete this.color,this.color=e}else this.color||(this.color=this.colorModel.defaultColor)}attributeChangedCallback(e,t,o){const i=this.colorModel.fromAttr(o);this[oe](i)||(this.color=i)}handleEvent(e){const t=this[Ke],o={...t,...e.detail};this[fe](o);let i;!Kt(o,t)&&!this[oe](i=this.colorModel.fromHsva(o))&&(this[ge]=i,Ie(this,"color-changed",{value:i}))}[oe](e){return this.color&&this.colorModel.equal(e,this.color)}[fe](e){this[Ke]=e,this[Ye].forEach(t=>t.update(e))}};class io extends Ne{constructor(e){super(e,"alpha",'aria-label="Alpha" aria-valuemin="0" aria-valuemax="1"',!1)}update(e){this.hsva=e;const t=me({...e,a:0}),o=me({...e,a:1}),i=e.a*100;this.style([{left:`${i}%`,color:me(e)},{"--gradient":`linear-gradient(90deg, ${t}, ${o}`}]);const n=C(i);this.el.setAttribute("aria-valuenow",`${n}`),this.el.setAttribute("aria-valuetext",`${n}%`)}getMove(e,t){return{a:t?W(this.hsva.a+e.x):e.x}}}const no=`[part=alpha]{flex:0 0 24px}[part=alpha]::after{display:block;content:"";position:absolute;top:0;left:0;right:0;bottom:0;border-radius:inherit;background-image:var(--gradient);box-shadow:inset 0 0 0 1px rgba(0,0,0,.05)}[part^=alpha]{background-color:#fff;background-image:url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill-opacity=".05"><rect x="8" width="8" height="8"/><rect y="8" width="8" height="8"/></svg>')}[part=alpha-pointer]{top:50%}`;class ro extends so{get[le](){return[...super[le],no]}get[de](){return[...super[de],io]}}const ao={defaultColor:"rgba(0, 0, 0, 1)",toHsva:Gt,fromHsva:jt,equal:Yt,fromAttr:s=>s};class lo extends ro{get colorModel(){return ao}}/**
 * @license
 * Copyright (c) 2017 - 2023 Vaadin Ltd.
 * This program is available under Apache License Version 2.0, available at https://vaadin.com/license/
 */function co(s){const e=[];for(;s;){if(s.nodeType===Node.DOCUMENT_NODE){e.push(s);break}if(s.nodeType===Node.DOCUMENT_FRAGMENT_NODE){e.push(s),s=s.host;continue}if(s.assignedSlot){s=s.assignedSlot;continue}s=s.parentNode}return e}const ye={start:"top",end:"bottom"},be={start:"left",end:"right"},Je=new ResizeObserver(s=>{setTimeout(()=>{s.forEach(e=>{e.target.__overlay&&e.target.__overlay._updatePosition()})})}),ho=s=>class extends s{static get properties(){return{positionTarget:{type:Object,value:null},horizontalAlign:{type:String,value:"start"},verticalAlign:{type:String,value:"top"},noHorizontalOverlap:{type:Boolean,value:!1},noVerticalOverlap:{type:Boolean,value:!1},requiredVerticalSpace:{type:Number,value:0}}}static get observers(){return["__positionSettingsChanged(horizontalAlign, verticalAlign, noHorizontalOverlap, noVerticalOverlap, requiredVerticalSpace)","__overlayOpenedChanged(opened, positionTarget)"]}constructor(){super(),this.__onScroll=this.__onScroll.bind(this),this._updatePosition=this._updatePosition.bind(this)}connectedCallback(){super.connectedCallback(),this.opened&&this.__addUpdatePositionEventListeners()}disconnectedCallback(){super.disconnectedCallback(),this.__removeUpdatePositionEventListeners()}__addUpdatePositionEventListeners(){window.addEventListener("resize",this._updatePosition),this.__positionTargetAncestorRootNodes=co(this.positionTarget),this.__positionTargetAncestorRootNodes.forEach(t=>{t.addEventListener("scroll",this.__onScroll,!0)})}__removeUpdatePositionEventListeners(){window.removeEventListener("resize",this._updatePosition),this.__positionTargetAncestorRootNodes&&(this.__positionTargetAncestorRootNodes.forEach(t=>{t.removeEventListener("scroll",this.__onScroll,!0)}),this.__positionTargetAncestorRootNodes=null)}__overlayOpenedChanged(t,o){if(this.__removeUpdatePositionEventListeners(),o&&(o.__overlay=null,Je.unobserve(o),t&&(this.__addUpdatePositionEventListeners(),o.__overlay=this,Je.observe(o))),t){const i=getComputedStyle(this);this.__margins||(this.__margins={},["top","bottom","left","right"].forEach(n=>{this.__margins[n]=parseInt(i[n],10)})),this.setAttribute("dir",i.direction),this._updatePosition(),requestAnimationFrame(()=>this._updatePosition())}}__positionSettingsChanged(){this._updatePosition()}__onScroll(t){this.contains(t.target)||this._updatePosition()}_updatePosition(){if(!this.positionTarget||!this.opened)return;const t=this.positionTarget.getBoundingClientRect(),o=this.__shouldAlignStartVertically(t);this.style.justifyContent=o?"flex-start":"flex-end";const i=this.__isRTL,n=this.__shouldAlignStartHorizontally(t,i),r=!i&&n||i&&!n;this.style.alignItems=r?"flex-start":"flex-end";const a=this.getBoundingClientRect(),h=this.__calculatePositionInOneDimension(t,a,this.noVerticalOverlap,ye,this,o),p=this.__calculatePositionInOneDimension(t,a,this.noHorizontalOverlap,be,this,n);Object.assign(this.style,h,p),this.toggleAttribute("bottom-aligned",!o),this.toggleAttribute("top-aligned",o),this.toggleAttribute("end-aligned",!r),this.toggleAttribute("start-aligned",r)}__shouldAlignStartHorizontally(t,o){const i=Math.max(this.__oldContentWidth||0,this.$.overlay.offsetWidth);this.__oldContentWidth=this.$.overlay.offsetWidth;const n=Math.min(window.innerWidth,document.documentElement.clientWidth),r=!o&&this.horizontalAlign==="start"||o&&this.horizontalAlign==="end";return this.__shouldAlignStart(t,i,n,this.__margins,r,this.noHorizontalOverlap,be)}__shouldAlignStartVertically(t){const o=this.requiredVerticalSpace||Math.max(this.__oldContentHeight||0,this.$.overlay.offsetHeight);this.__oldContentHeight=this.$.overlay.offsetHeight;const i=Math.min(window.innerHeight,document.documentElement.clientHeight),n=this.verticalAlign==="top";return this.__shouldAlignStart(t,o,i,this.__margins,n,this.noVerticalOverlap,ye)}__shouldAlignStart(t,o,i,n,r,a,h){const p=i-t[a?h.end:h.start]-n[h.end],g=t[a?h.start:h.end]-n[h.start],x=r?p:g,z=x>(r?g:p)||x>o;return r===z}__adjustBottomProperty(t,o,i){let n;if(t===o.end){if(o.end===ye.end){const r=Math.min(window.innerHeight,document.documentElement.clientHeight);if(i>r&&this.__oldViewportHeight){const a=this.__oldViewportHeight-r;n=i-a}this.__oldViewportHeight=r}if(o.end===be.end){const r=Math.min(window.innerWidth,document.documentElement.clientWidth);if(i>r&&this.__oldViewportWidth){const a=this.__oldViewportWidth-r;n=i-a}this.__oldViewportWidth=r}}return n}__calculatePositionInOneDimension(t,o,i,n,r,a){const h=a?n.start:n.end,p=a?n.end:n.start,g=parseFloat(r.style[h]||getComputedStyle(r)[h]),x=this.__adjustBottomProperty(h,n,g),D=o[a?n.start:n.end]-t[i===a?n.end:n.start],z=x?`${x}px`:`${g+D*(a?-1:1)}px`;return{[h]:z,[p]:""}}};class po extends CustomEvent{constructor(e){super("color-picker-change",{detail:{value:e}})}}const ct=y`
  :host {
    --preview-size: 24px;
    --preview-color: rgba(0, 0, 0, 0);
  }

  .preview {
    --preview-bg-size: calc(var(--preview-size) / 2);
    --preview-bg-pos: calc(var(--preview-size) / 4);

    width: var(--preview-size);
    height: var(--preview-size);
    padding: 0;
    position: relative;
    overflow: hidden;
    background: none;
    border: solid 2px #888;
    border-radius: 4px;
    box-sizing: content-box;
  }

  .preview::before,
  .preview::after {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
  }

  .preview::before {
    content: '';
    background: white;
    background-image: linear-gradient(45deg, #666 25%, transparent 25%),
      linear-gradient(45deg, transparent 75%, #666 75%), linear-gradient(45deg, transparent 75%, #666 75%),
      linear-gradient(45deg, #666 25%, transparent 25%);
    background-size: var(--preview-bg-size) var(--preview-bg-size);
    background-position: 0 0, 0 0, calc(var(--preview-bg-pos) * -1) calc(var(--preview-bg-pos) * -1),
      var(--preview-bg-pos) var(--preview-bg-pos);
  }

  .preview::after {
    content: '';
    background-color: var(--preview-color);
  }
`;let Z=class extends T{constructor(){super(...arguments),this.commitValue=!1}static get styles(){return[ct,y`
        #toggle {
          display: block;
        }
      `]}update(e){super.update(e),e.has("value")&&this.overlay&&this.overlay.requestContentUpdate()}firstUpdated(){this.overlay=document.createElement("vaadin-dev-tools-color-picker-overlay"),this.overlay.renderer=this.renderOverlayContent.bind(this),this.overlay.owner=this,this.overlay.positionTarget=this.toggle,this.overlay.noVerticalOverlap=!0,this.overlay.addEventListener("vaadin-overlay-escape-press",this.handleOverlayEscape.bind(this)),this.overlay.addEventListener("vaadin-overlay-close",this.handleOverlayClose.bind(this)),this.append(this.overlay)}render(){const e=this.value||"rgba(0, 0, 0, 0)";return c` <button
      id="toggle"
      class="preview"
      style="--preview-color: ${e}"
      @click=${this.open}
    ></button>`}open(){this.commitValue=!1,this.overlay.opened=!0,this.overlay.style.zIndex="1000000";const e=this.overlay.shadowRoot.querySelector('[part="overlay"]');e.style.background="#333"}renderOverlayContent(e){const o=getComputedStyle(this.toggle,"::after").getPropertyValue("background-color");Se(c` <div>
        <vaadin-dev-tools-color-picker-overlay-content
          .value=${o}
          .presets=${this.presets}
          @color-changed=${this.handleColorChange.bind(this)}
        ></vaadin-dev-tools-color-picker-overlay-content>
      </div>`,e)}handleColorChange(e){this.commitValue=!0,this.dispatchEvent(new po(e.detail.value)),e.detail.close&&(this.overlay.opened=!1,this.handleOverlayClose())}handleOverlayEscape(){this.commitValue=!1}handleOverlayClose(){const e=this.commitValue?"color-picker-commit":"color-picker-cancel";this.dispatchEvent(new CustomEvent(e))}};l([m({})],Z.prototype,"value",void 0);l([m({})],Z.prototype,"presets",void 0);l([Q("#toggle")],Z.prototype,"toggle",void 0);Z=l([k("vaadin-dev-tools-color-picker")],Z);let ce=class extends T{static get styles(){return[ct,y`
        :host {
          display: block;
          padding: 12px;
        }

        .picker::part(saturation),
        .picker::part(hue) {
          margin-bottom: 10px;
        }

        .picker::part(hue),
        .picker::part(alpha) {
          flex: 0 0 20px;
        }

        .picker::part(saturation),
        .picker::part(hue),
        .picker::part(alpha) {
          border-radius: 3px;
        }

        .picker::part(saturation-pointer),
        .picker::part(hue-pointer),
        .picker::part(alpha-pointer) {
          width: 20px;
          height: 20px;
        }

        .swatches {
          display: grid;
          grid-template-columns: repeat(6, var(--preview-size));
          grid-column-gap: 10px;
          grid-row-gap: 6px;
          margin-top: 16px;
        }
      `]}render(){return c` <div>
      <vaadin-dev-tools-rgba-string-color-picker
        class="picker"
        .color=${this.value}
        @color-changed=${this.handlePickerChange}
      ></vaadin-dev-tools-rgba-string-color-picker>
      ${this.renderSwatches()}
    </div>`}renderSwatches(){if(!this.presets||this.presets.length===0)return;const e=this.presets.map(t=>c` <button
        class="preview"
        style="--preview-color: ${t}"
        @click=${()=>this.selectPreset(t)}
      ></button>`);return c` <div class="swatches">${e}</div>`}handlePickerChange(e){this.dispatchEvent(new CustomEvent("color-changed",{detail:{value:e.detail.value}}))}selectPreset(e){this.dispatchEvent(new CustomEvent("color-changed",{detail:{value:e,close:!0}}))}};l([m({})],ce.prototype,"value",void 0);l([m({})],ce.prototype,"presets",void 0);ce=l([k("vaadin-dev-tools-color-picker-overlay-content")],ce);customElements.whenDefined("vaadin-overlay").then(()=>{const s=customElements.get("vaadin-overlay");class e extends ho(s){}customElements.define("vaadin-dev-tools-color-picker-overlay",e)});customElements.define("vaadin-dev-tools-rgba-string-color-picker",lo);let Xe=class extends A{constructor(){super(...arguments),this.presets=new ne}static get styles(){return[A.styles,y`
        .editor-row {
          align-items: center;
        }

        .editor-row > .editor {
          display: flex;
          align-items: center;
          gap: 0.5rem;
        }
      `]}update(e){e.has("propertyMetadata")&&(this.presets=new ne(this.propertyMetadata)),super.update(e)}renderEditor(){var e;return c`
      <vaadin-dev-tools-color-picker
        .value=${this.value}
        .presets=${this.presets.values}
        @color-picker-change=${this.handleColorPickerChange}
        @color-picker-commit=${this.handleColorPickerCommit}
        @color-picker-cancel=${this.handleColorPickerCancel}
      ></vaadin-dev-tools-color-picker>
      <vaadin-dev-tools-theme-text-input
        .value=${this.value}
        .showClearButton=${((e=this.propertyValue)==null?void 0:e.modified)||!1}
        @change=${this.handleInputChange}
      ></vaadin-dev-tools-theme-text-input>
    `}handleInputChange(e){this.value=e.detail.value,this.dispatchChange(this.value)}handleColorPickerChange(e){this.value=e.detail.value}handleColorPickerCommit(){this.dispatchChange(this.value)}handleColorPickerCancel(){this.updateValueFromTheme()}dispatchChange(e){const t=this.presets.tryMapToPreset(e);super.dispatchChange(t)}updateValueFromTheme(){var e;super.updateValueFromTheme(),this.value=this.presets.tryMapToRawValue(((e=this.propertyValue)==null?void 0:e.value)||"")}};Xe=l([k("vaadin-dev-tools-theme-color-property-editor")],Xe);class uo extends CustomEvent{constructor(e){super("open-css",{detail:{element:e}})}}let he=class extends T{static get styles(){return y`
      .section .header {
        display: flex;
        align-items: baseline;
        justify-content: space-between;
        padding: 0.4rem var(--theme-editor-section-horizontal-padding);
        color: var(--dev-tools-text-color-emphasis);
        background-color: rgba(0, 0, 0, 0.2);
      }

      .section .property-list .property-editor:not(:last-child) {
        border-bottom: solid 1px rgba(0, 0, 0, 0.2);
      }

      .section .header .open-css {
        all: initial;
        font-family: inherit;
        font-size: var(--dev-tools-font-size-small);
        line-height: 1;
        white-space: nowrap;
        background-color: rgba(255, 255, 255, 0.12);
        color: var(--dev-tools-text-color);
        font-weight: 600;
        padding: 0.25rem 0.375rem;
        border-radius: 0.25rem;
      }

      .section .header .open-css:hover {
        color: var(--dev-tools-text-color-emphasis);
      }
    `}render(){const e=this.metadata.elements.map(t=>this.renderSection(t));return c` <div>${e}</div> `}renderSection(e){const t=e.properties.map(o=>this.renderPropertyEditor(e,o));return c`
      <div class="section" data-testid=${e==null?void 0:e.displayName}>
        <div class="header">
          <span> ${e.displayName} </span>
          <button class="open-css" @click=${()=>this.handleOpenCss(e)}>Edit CSS</button>
        </div>
        <div class="property-list">${t}</div>
      </div>
    `}handleOpenCss(e){this.dispatchEvent(new uo(e))}renderPropertyEditor(e,t){let o;switch(t.editorType){case b.checkbox:o=ee`vaadin-dev-tools-theme-checkbox-property-editor`;break;case b.range:o=ee`vaadin-dev-tools-theme-range-property-editor`;break;case b.color:o=ee`vaadin-dev-tools-theme-color-property-editor`;break;default:o=ee`vaadin-dev-tools-theme-text-property-editor`}return ut` <${o}
          class="property-editor"
          .elementMetadata=${e}
          .propertyMetadata=${t}
          .theme=${this.theme}
          data-testid=${t.propertyName}
        >
        </${o}>`}};l([m({})],he.prototype,"metadata",void 0);l([m({})],he.prototype,"theme",void 0);he=l([k("vaadin-dev-tools-theme-property-list")],he);let pe=class extends T{render(){return c`<div
      tabindex="-1"
      @mousemove=${this.onMouseMove}
      @click=${this.onClick}
      @keydown=${this.onKeyDown}
    ></div>`}onClick(e){const t=this.getTargetElement(e);this.dispatchEvent(new CustomEvent("shim-click",{detail:{target:t}}))}onMouseMove(e){const t=this.getTargetElement(e);this.dispatchEvent(new CustomEvent("shim-mousemove",{detail:{target:t}}))}onKeyDown(e){this.dispatchEvent(new CustomEvent("shim-keydown",{detail:{originalEvent:e}}))}getTargetElement(e){this.style.display="none";const t=document.elementFromPoint(e.clientX,e.clientY);return this.style.display="",t}};pe.shadowRootOptions={...T.shadowRootOptions,delegatesFocus:!0};pe.styles=[y`
      div {
        pointer-events: auto;
        background: rgba(255, 255, 255, 0);
        position: fixed;
        inset: 0px;
        z-index: 1000000;
      }
    `];pe=l([k("vaadin-dev-tools-shim")],pe);const vo={resolve:s=>P(o=>o.classList.contains("cc-banner"),s)?document.querySelector("vaadin-cookie-consent"):void 0},mo={resolve:s=>{const t=P(o=>o.localName==="vaadin-login-overlay-wrapper",s);return t?t.__dataHost:void 0}},go={resolve:s=>s.localName==="vaadin-dialog-overlay"?s.__dataHost:void 0},fo={resolve:s=>{const t=P(o=>o.localName==="vaadin-confirm-dialog-overlay",s);return t?t.__dataHost:void 0}},yo={resolve:s=>{const t=P(o=>o.localName==="vaadin-notification-card",s);return t?t.__dataHost:void 0}},bo={resolve:s=>s.localName!=="vaadin-menu-bar-item"?void 0:P(t=>t.localName==="vaadin-menu-bar",s)},Ze=[vo,mo,go,fo,yo,bo],wo={resolve:s=>P(t=>t.classList.contains("cc-banner"),s)},_o={resolve:s=>{var o;const t=P(i=>{var n;return((n=i.shadowRoot)==null?void 0:n.querySelector("[part=overlay]"))!=null},s);return(o=t==null?void 0:t.shadowRoot)==null?void 0:o.querySelector("[part=overlay]")}},xo={resolve:s=>{var o;const t=P(i=>i.localName==="vaadin-login-overlay-wrapper",s);return(o=t==null?void 0:t.shadowRoot)==null?void 0:o.querySelector("[part=card]")}},Qe=[xo,wo,_o],P=function(s,e){return s(e)?e:e.parentNode&&e.parentNode instanceof HTMLElement?P(s,e.parentNode):void 0};class Eo{resolveElement(e){for(const t in Ze){let o=e;if((o=Ze[t].resolve(e))!==void 0)return o}return e}}class ko{resolveElement(e){for(const t in Qe){let o=e;if((o=Qe[t].resolve(e))!==void 0)return o}return e}}const Co=new Eo,So=new ko;let M=class extends T{constructor(){super(),this.active=!1,this.components=[],this.selected=0,this.mouseMoveEvent=this.mouseMoveEvent.bind(this)}connectedCallback(){super.connectedCallback();const e=new CSSStyleSheet;e.replaceSync(`
    .vaadin-dev-tools-highlight-overlay {
      pointer-events: none;
      position: absolute;
      z-index: 10000;
      background: rgba(158,44,198,0.25);
    }`),document.adoptedStyleSheets=[...document.adoptedStyleSheets,e],this.overlayElement=document.createElement("div"),this.overlayElement.classList.add("vaadin-dev-tools-highlight-overlay"),this.addEventListener("mousemove",this.mouseMoveEvent)}disconnectedCallback(){super.disconnectedCallback(),this.removeEventListener("mousemove",this.mouseMoveEvent)}render(){var e;return this.active?(this.style.display="block",c`
      <vaadin-dev-tools-shim
        @shim-click=${this.shimClick}
        @shim-mousemove=${this.shimMove}
        @shim-keydown=${this.shimKeydown}
      ></vaadin-dev-tools-shim>
      <div class="window popup component-picker-info">${(e=this.options)==null?void 0:e.infoTemplate}</div>
      <div class="window popup component-picker-components-info">
        <div>
          ${this.components.map((t,o)=>c`<div class=${o===this.selected?"selected":""}>
                ${t.element.tagName.toLowerCase()}
              </div>`)}
        </div>
      </div>
    `):(this.style.display="none",null)}open(e){this.options=e,this.active=!0,this.dispatchEvent(new CustomEvent("component-picker-opened",{}))}close(){this.active=!1,this.dispatchEvent(new CustomEvent("component-picker-closed",{}))}update(e){if(super.update(e),(e.has("selected")||e.has("components"))&&this.highlight(this.components[this.selected]),e.has("active")){const t=e.get("active"),o=this.active;!t&&o?requestAnimationFrame(()=>this.shim.focus()):t&&!o&&this.highlight(void 0)}}mouseMoveEvent(e){var o;if(!this.active){this.style.display="none";return}const t=(o=this.shadowRoot)==null?void 0:o.querySelector(".component-picker-info");if(t){const i=t.getBoundingClientRect();e.x>i.x&&e.x<i.x+i.width&&e.y>i.y&&e.y<=i.y+i.height?t.style.opacity="0.05":t.style.opacity="1.0"}}shimKeydown(e){const t=e.detail.originalEvent;if(t.key==="Escape")this.close(),e.stopPropagation(),e.preventDefault();else if(t.key==="ArrowUp"){let o=this.selected-1;o<0&&(o=this.components.length-1),this.selected=o}else t.key==="ArrowDown"?this.selected=(this.selected+1)%this.components.length:t.key==="Enter"&&(this.pickSelectedComponent(),e.stopPropagation(),e.preventDefault())}shimMove(e){const t=Co.resolveElement(e.detail.target);this.components=wt(t),this.selected=this.components.length-1,this.components[this.selected].highlightElement=So.resolveElement(e.detail.target)}shimClick(e){this.pickSelectedComponent()}pickSelectedComponent(){const e=this.components[this.selected];if(e&&this.options)try{this.options.pickCallback(e)}catch(t){console.error("Pick callback failed",t)}this.close()}highlight(e){let t=(e==null?void 0:e.highlightElement)??(e==null?void 0:e.element);if(this.highlighted!==t)if(t){const o=t.getBoundingClientRect(),i=getComputedStyle(t);this.overlayElement.style.top=`${o.top}px`,this.overlayElement.style.left=`${o.left}px`,this.overlayElement.style.width=`${o.width}px`,this.overlayElement.style.height=`${o.height}px`,this.overlayElement.style.borderRadius=i.borderRadius,document.body.append(this.overlayElement)}else this.overlayElement.remove();this.highlighted=t}};M.styles=[nt,y`
      .component-picker-info {
        left: 1em;
        bottom: 1em;
      }

      .component-picker-components-info {
        right: 3em;
        bottom: 1em;
      }

      .component-picker-components-info .selected {
        font-weight: bold;
      }
    `];l([w()],M.prototype,"active",void 0);l([w()],M.prototype,"components",void 0);l([w()],M.prototype,"selected",void 0);l([Q("vaadin-dev-tools-shim")],M.prototype,"shim",void 0);M=l([k("vaadin-dev-tools-component-picker")],M);const To=Object.freeze(Object.defineProperty({__proto__:null,get ComponentPicker(){return M}},Symbol.toStringTag,{value:"Module"}));class Ao{constructor(){this.currentActiveComponent=null,this.currentActiveComponentMetaData=null,this.componentPicked=async(e,t)=>{await this.hideOverlay(),this.currentActiveComponent=e,this.currentActiveComponentMetaData=t},this.showOverlay=()=>{!this.currentActiveComponent||!this.currentActiveComponentMetaData||this.currentActiveComponentMetaData.openOverlay&&this.currentActiveComponentMetaData.openOverlay(this.currentActiveComponent)},this.hideOverlay=()=>{!this.currentActiveComponent||!this.currentActiveComponentMetaData||this.currentActiveComponentMetaData.hideOverlay&&this.currentActiveComponentMetaData.hideOverlay(this.currentActiveComponent)},this.reset=()=>{this.currentActiveComponent=null,this.currentActiveComponentMetaData=null}}}const H=new Ao,ss=s=>{const t=s.element.$.comboBox,o=t.$.overlay;Ro(s.element,t,o)},is=s=>{const e=s.element,t=e.$.comboBox,o=t.$.overlay;Io(e,t,o)},Ro=(s,e,t)=>{s.opened=!0,t._storedModeless=t.modeless,t.modeless=!0,document._themeEditorDocClickListener=No(s,e),document.addEventListener("click",document._themeEditorDocClickListener),e.removeEventListener("focusout",e._boundOnFocusout)},Io=(s,e,t)=>{s.opened=!1,!(!e||!t)&&(t.modeless=t._storedModeless,delete t._storedModeless,e.addEventListener("focusout",e._boundOnFocusout),document.removeEventListener("click",document._themeEditorDocClickListener),delete document._themeEditorDocClickListener)},No=(s,e)=>t=>{const o=t.target;o!=null&&(e.opened=!$o(o,s))};function $o(s,e){if(!s||!s.tagName)return!0;if(s.tagName.startsWith("VAADIN-DEV"))return!1;let t=s,o={nodeId:-1,uiId:-1,element:void 0};for(;t&&t.parentNode&&(o=we(t),o.nodeId===-1);)t=t.parentElement?t.parentElement:t.parentNode.host;const i=we(e);return!(o.nodeId!==-1&&i.nodeId===o.nodeId)}Re(y`
  .vaadin-theme-editor-highlight {
    outline: solid 2px #9e2cc6;
    outline-offset: 3px;
  }
`);let L=class extends T{constructor(){super(...arguments),this.expanded=!1,this.themeEditorState=j.enabled,this.context=null,this.baseTheme=null,this.editedTheme=null,this.effectiveTheme=null,this.markedAsUsed=!1}static get styles(){return y`
      :host {
        animation: fade-in var(--dev-tools-transition-duration) ease-in;
        --theme-editor-section-horizontal-padding: 0.75rem;
        display: flex;
        flex-direction: column;
        max-height: 400px;
      }

      .notice {
        padding: var(--theme-editor-section-horizontal-padding);
      }

      .notice a {
        color: var(--dev-tools-text-color-emphasis);
      }

      .hint vaadin-icon {
        color: var(--dev-tools-green-color);
        font-size: var(--lumo-icon-size-m);
      }

      .hint {
        display: flex;
        align-items: center;
        gap: var(--theme-editor-section-horizontal-padding);
      }

      .header {
        flex: 0 0 auto;
        border-bottom: solid 1px rgba(0, 0, 0, 0.2);
      }

      .header .picker-row {
        padding: var(--theme-editor-section-horizontal-padding);
        display: flex;
        gap: 20px;
        align-items: center;
        justify-content: space-between;
      }

      .picker {
        flex: 1 1 0;
        min-width: 0;
        display: flex;
        align-items: center;
      }

      .picker button {
        min-width: 0;
        display: inline-flex;
        align-items: center;
        padding: 0;
        line-height: 20px;
        border: none;
        background: none;
        color: var(--dev-tools-text-color);
      }

      .picker button:not(:disabled):hover {
        color: var(--dev-tools-text-color-emphasis);
      }

      .picker svg,
      .picker .component-type {
        flex: 0 0 auto;
        margin-right: 4px;
      }

      .picker .instance-name {
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        color: #e5a2fce5;
      }

      .picker .instance-name-quote {
        color: #e5a2fce5;
      }

      .picker .no-selection {
        font-style: italic;
      }

      .actions {
        display: flex;
        align-items: center;
        gap: 8px;
      }

      .property-list {
        flex: 1 1 auto;
        overflow-y: auto;
      }

      .link-button {
        all: initial;
        font-family: inherit;
        font-size: var(--dev-tools-font-size-small);
        line-height: 1;
        white-space: nowrap;
        color: inherit;
        font-weight: 600;
        text-decoration: underline;
      }

      .link-button:focus,
      .link-button:hover {
        color: var(--dev-tools-text-color-emphasis);
      }

      .icon-button {
        padding: 0;
        line-height: 0;
        border: none;
        background: none;
        color: var(--dev-tools-text-color);
      }

      .icon-button:disabled {
        opacity: 0.5;
      }

      .icon-button:not(:disabled):hover {
        color: var(--dev-tools-text-color-emphasis);
      }
    `}firstUpdated(){this.api=new zt(this.connection),this.history=new Ut(this.api),this.historyActions=this.history.allowedActions,this.undoRedoListener=e=>{var o,i;const t=e.key==="Z"||e.key==="z";t&&(e.ctrlKey||e.metaKey)&&e.shiftKey?(o=this.historyActions)!=null&&o.allowRedo&&this.handleRedo():t&&(e.ctrlKey||e.metaKey)&&(i=this.historyActions)!=null&&i.allowUndo&&this.handleUndo()},document.addEventListener("vaadin-theme-updated",()=>{U.clear(),this.refreshTheme()}),document.addEventListener("keydown",this.undoRedoListener)}activate(){this.dispatchEvent(new CustomEvent("before-open"))}deactivate(){this.dispatchEvent(new CustomEvent("after-close"))}update(e){var t,o;super.update(e),e.has("expanded")&&(this.expanded?(this.highlightElement((t=this.context)==null?void 0:t.component.element),H.showOverlay()):(H.hideOverlay(),this.removeElementHighlight((o=this.context)==null?void 0:o.component.element)))}disconnectedCallback(){var e;super.disconnectedCallback(),this.removeElementHighlight((e=this.context)==null?void 0:e.component.element),H.hideOverlay(),H.reset(),document.removeEventListener("keydown",this.undoRedoListener)}render(){var e,t,o;return this.themeEditorState===j.missing_theme?this.renderMissingThemeNotice():c`
      <div class="header">
        <div class="picker-row">
          ${this.renderPicker()}
          <div class="actions">
            ${(e=this.context)!=null&&e.metadata?c` <vaadin-dev-tools-theme-scope-selector
                  .value=${this.context.scope}
                  .metadata=${this.context.metadata}
                  @scope-change=${this.handleScopeChange}
                ></vaadin-dev-tools-theme-scope-selector>`:null}
            <button
              class="icon-button"
              data-testid="undo"
              ?disabled=${!((t=this.historyActions)!=null&&t.allowUndo)}
              @click=${this.handleUndo}
            >
              ${se.undo}
            </button>
            <button
              class="icon-button"
              data-testid="redo"
              ?disabled=${!((o=this.historyActions)!=null&&o.allowRedo)}
              @click=${this.handleRedo}
            >
              ${se.redo}
            </button>
          </div>
        </div>
        ${this.renderLocalClassNameEditor()}
      </div>
      ${this.renderPropertyList()}
    `}renderMissingThemeNotice(){return c`
      <div class="notice">
        It looks like you have not set up an application theme yet. Theme editor requires an existing theme to work
        with. Please check our
        <a href="https://vaadin.com/docs/latest/styling/application-theme" target="_blank">documentation</a>
        on how to set up an application theme.
      </div>
    `}renderPropertyList(){if(!this.context)return null;if(!this.context.metadata){const t=this.context.component.element.localName;return c`
        <div class="notice">Styling <code>&lt;${t}&gt;</code> components is not supported at the moment.</div>
      `}if(this.context.scope===_.local&&!this.context.accessible){const t=this.context.metadata.displayName;return c`
        ${this.context.metadata.notAccessibleDescription&&this.context.scope===_.local?c`<div class="notice hint" style="padding-bottom: 0;">
              <vaadin-icon icon="vaadin:lightbulb"></vaadin-icon>
              <div>${this.context.metadata.notAccessibleDescription}</div>
            </div>`:""}
        <div class="notice">
          The selected ${t} cannot be styled locally. Currently, Theme Editor only supports styling
          instances that are assigned to a local variable, like so:
          <pre><code>Button saveButton = new Button("Save");</code></pre>
          If you want to modify the code so that it satisfies this requirement,
          <button class="link-button" @click=${this.handleShowComponent}>click here</button>
          to open it in your IDE. Alternatively you can choose to style all ${t}s by selecting "Global" from
          the scope dropdown above.
        </div>
      `}return c` ${this.context.metadata.description&&this.context.scope===_.local?c`<div class="notice hint">
            <vaadin-icon icon="vaadin:lightbulb"></vaadin-icon>
            <div>${this.context.metadata.description}</div>
          </div>`:""}
      <vaadin-dev-tools-theme-property-list
        class="property-list"
        .metadata=${this.context.metadata}
        .theme=${this.effectiveTheme}
        @theme-property-value-change=${this.handlePropertyChange}
        @open-css=${this.handleOpenCss}
      ></vaadin-dev-tools-theme-property-list>`}handleShowComponent(){if(!this.context)return;const e=this.context.component,t={nodeId:e.nodeId,uiId:e.uiId};this.connection.sendShowComponentCreateLocation(t)}async handleOpenCss(e){if(!this.context)return;await this.ensureLocalClassName();const t={themeScope:this.context.scope,localClassName:this.context.localClassName},o=G(e.detail.element,t);await this.api.openCss(o)}renderPicker(){var t;let e;if((t=this.context)!=null&&t.metadata){const o=this.context.scope===_.local?this.context.metadata.displayName:`All ${this.context.metadata.displayName}s`,i=c`<span class="component-type">${o}</span>`,n=this.context.scope===_.local?Mt(this.context.component):null,r=n?c` <span class="instance-name-quote">"</span><span class="instance-name">${n}</span
            ><span class="instance-name-quote">"</span>`:null;e=c`${i} ${r}`}else e=c`<span class="no-selection">Pick an element to get started</span>`;return c`
      <div class="picker">
        <button @click=${this.pickComponent}>${se.crosshair} ${e}</button>
      </div>
    `}renderLocalClassNameEditor(){var o;const e=((o=this.context)==null?void 0:o.scope)===_.local&&this.context.accessible;if(!this.context||!e)return null;const t=this.context.localClassName||this.context.suggestedClassName;return c` <vaadin-dev-tools-theme-class-name-editor
      .className=${t}
      @class-name-change=${this.handleClassNameChange}
    >
    </vaadin-dev-tools-theme-class-name-editor>`}async handleClassNameChange(e){if(!this.context)return;const t=this.context.localClassName,o=e.detail.value;if(t){const i=this.context.component.element;this.context.localClassName=o;const n=await this.api.setLocalClassName(this.context.component,o);this.historyActions=this.history.push(n.requestId,()=>U.previewLocalClassName(i,o),()=>U.previewLocalClassName(i,t))}else this.context={...this.context,suggestedClassName:o}}async pickComponent(){var e;H.hideOverlay(),this.removeElementHighlight((e=this.context)==null?void 0:e.component.element),this.pickerProvider().open({infoTemplate:c`
        <div>
          <h3>Locate the component to style</h3>
          <p>Use the mouse cursor to highlight components in the UI.</p>
          <p>Use arrow down/up to cycle through and highlight specific components under the cursor.</p>
          <p>Click the primary mouse button to select the component.</p>
        </div>
      `,pickCallback:async t=>{var i;const o=await Ot.getMetadata(t);if(!o){this.context={component:t,scope:((i=this.context)==null?void 0:i.scope)||_.local},this.baseTheme=null,this.editedTheme=null,this.effectiveTheme=null;return}await H.componentPicked(t,o),this.highlightElement(t.element),this.refreshComponentAndTheme(t,o),H.showOverlay()}})}handleScopeChange(e){this.context&&this.refreshTheme({...this.context,scope:e.detail.value})}async handlePropertyChange(e){if(!this.context||!this.baseTheme||!this.editedTheme)return;const{element:t,property:o,value:i}=e.detail;this.editedTheme.updatePropertyValue(t.selector,o.propertyName,i,!0),this.effectiveTheme=V.combine(this.baseTheme,this.editedTheme),await this.ensureLocalClassName();const n={themeScope:this.context.scope,localClassName:this.context.localClassName},r=Lt(t,n,o.propertyName,i);try{const a=await this.api.setCssRules([r]);this.historyActions=this.history.push(a.requestId);const h=Pt(r);U.add(h)}catch(a){console.error("Failed to update property value",a)}}async handleUndo(){this.historyActions=await this.history.undo(),await this.refreshComponentAndTheme()}async handleRedo(){this.historyActions=await this.history.redo(),await this.refreshComponentAndTheme()}async ensureLocalClassName(){if(!this.context||this.context.scope===_.global||this.context.localClassName)return;if(!this.context.localClassName&&!this.context.suggestedClassName)throw new Error("Cannot assign local class name for the component because it does not have a suggested class name");const e=this.context.component.element,t=this.context.suggestedClassName;this.context.localClassName=t;const o=await this.api.setLocalClassName(this.context.component,t);this.historyActions=this.history.push(o.requestId,()=>U.previewLocalClassName(e,t),()=>U.previewLocalClassName(e))}async refreshComponentAndTheme(e,t){var i,n,r;if(e=e||((i=this.context)==null?void 0:i.component),t=t||((n=this.context)==null?void 0:n.metadata),!e||!t)return;const o=await this.api.loadComponentMetadata(e);this.markedAsUsed||this.api.markAsUsed().then(()=>{this.markedAsUsed=!0}),U.previewLocalClassName(e.element,o.className),await this.refreshTheme({scope:((r=this.context)==null?void 0:r.scope)||_.local,metadata:t,component:e,localClassName:o.className,suggestedClassName:o.suggestedClassName,accessible:o.accessible})}async refreshTheme(e){const t=e||this.context;if(!t||!t.metadata)return;if(t.scope===_.local&&!t.accessible){this.context=t,this.baseTheme=null,this.editedTheme=null,this.effectiveTheme=null;return}let i=new V(t.metadata);if(!(t.scope===_.local&&!t.localClassName)){const a={themeScope:t.scope,localClassName:t.localClassName},h=t.metadata.elements.map(g=>G(g,a)),p=await this.api.loadRules(h);i=V.fromServerRules(t.metadata,a,p.rules)}const r=await Vt(t.metadata);this.context=t,this.baseTheme=r,this.editedTheme=i,this.effectiveTheme=V.combine(r,this.editedTheme)}highlightElement(e){e&&e.classList.add("vaadin-theme-editor-highlight")}removeElementHighlight(e){e&&e.classList.remove("vaadin-theme-editor-highlight")}};l([m({})],L.prototype,"expanded",void 0);l([m({})],L.prototype,"themeEditorState",void 0);l([m({})],L.prototype,"pickerProvider",void 0);l([m({})],L.prototype,"connection",void 0);l([w()],L.prototype,"historyActions",void 0);l([w()],L.prototype,"context",void 0);l([w()],L.prototype,"effectiveTheme",void 0);L=l([k("vaadin-dev-tools-theme-editor")],L);var Oo=function(){var s=document.getSelection();if(!s.rangeCount)return function(){};for(var e=document.activeElement,t=[],o=0;o<s.rangeCount;o++)t.push(s.getRangeAt(o));switch(e.tagName.toUpperCase()){case"INPUT":case"TEXTAREA":e.blur();break;default:e=null;break}return s.removeAllRanges(),function(){s.type==="Caret"&&s.removeAllRanges(),s.rangeCount||t.forEach(function(i){s.addRange(i)}),e&&e.focus()}},et={"text/plain":"Text","text/html":"Url",default:"Text"},Lo="Copy to clipboard: #{key}, Enter";function Po(s){var e=(/mac os x/i.test(navigator.userAgent)?"⌘":"Ctrl")+"+C";return s.replace(/#{\s*key\s*}/g,e)}function Vo(s,e){var t,o,i,n,r,a,h=!1;e||(e={}),t=e.debug||!1;try{i=Oo(),n=document.createRange(),r=document.getSelection(),a=document.createElement("span"),a.textContent=s,a.style.all="unset",a.style.position="fixed",a.style.top=0,a.style.clip="rect(0, 0, 0, 0)",a.style.whiteSpace="pre",a.style.webkitUserSelect="text",a.style.MozUserSelect="text",a.style.msUserSelect="text",a.style.userSelect="text",a.addEventListener("copy",function(g){if(g.stopPropagation(),e.format)if(g.preventDefault(),typeof g.clipboardData>"u"){t&&console.warn("unable to use e.clipboardData"),t&&console.warn("trying IE specific stuff"),window.clipboardData.clearData();var x=et[e.format]||et.default;window.clipboardData.setData(x,s)}else g.clipboardData.clearData(),g.clipboardData.setData(e.format,s);e.onCopy&&(g.preventDefault(),e.onCopy(g.clipboardData))}),document.body.appendChild(a),n.selectNodeContents(a),r.addRange(n);var p=document.execCommand("copy");if(!p)throw new Error("copy command was unsuccessful");h=!0}catch(g){t&&console.error("unable to copy using execCommand: ",g),t&&console.warn("trying IE specific stuff");try{window.clipboardData.setData(e.format||"text",s),e.onCopy&&e.onCopy(window.clipboardData),h=!0}catch(x){t&&console.error("unable to copy using clipboardData: ",x),t&&console.error("falling back to prompt"),o=Po("message"in e?e.message:Lo),window.prompt(o,s)}}finally{r&&(typeof r.removeRange=="function"?r.removeRange(n):r.removeAllRanges()),a&&document.body.removeChild(a),i()}return h}let ue=class extends T{constructor(){super(...arguments),this.serverInfo={versions:[]}}createRenderRoot(){return this}render(){return c` <div class="info-tray">
      <button class="button copy" @click=${this.copyInfoToClipboard}>Copy</button>
      <dl>
        ${this.serverInfo.versions.map(e=>c`
            <dt>${e.name}</dt>
            <dd>${e.version}</dd>
          `)}
        <dt>Browser</dt>
        <dd>${navigator.userAgent}</dd>
        <dt>
          Live reload
          <label class="switch">
            <input
              id="toggle"
              type="checkbox"
              ?disabled=${!this._devTools.conf.enable||(this._devTools.frontendStatus===u.UNAVAILABLE||this._devTools.frontendStatus===u.ERROR)&&(this._devTools.javaStatus===u.UNAVAILABLE||this._devTools.javaStatus===u.ERROR)}
              ?checked="${this._devTools.frontendStatus===u.ACTIVE||this._devTools.javaStatus===u.ACTIVE}"
              @change=${e=>this._devTools.setActive(e.target.checked)}
            />
            <span class="slider"></span>
          </label>
        </dt>
        <dd
          class="live-reload-status"
          style="--status-color: ${this._devTools.getStatusColor(this._devTools.javaStatus)}"
        >
          Java ${this._devTools.javaStatus}
          ${this._devTools.conf.backend?`(${v.BACKEND_DISPLAY_NAME[this._devTools.conf.backend]})`:""}
        </dd>
        <dd
          class="live-reload-status"
          style="--status-color: ${this._devTools.getStatusColor(this._devTools.frontendStatus)}"
        >
          Front end ${this._devTools.frontendStatus}
        </dd>
      </dl>
    </div>`}handleMessage(e){return(e==null?void 0:e.command)==="serverInfo"?(this.serverInfo=e.data,!0):!1}copyInfoToClipboard(){const e=this.renderRoot.querySelectorAll(".info-tray dt, .info-tray dd"),t=Array.from(e).map(o=>(o.localName==="dd"?": ":`
`)+o.textContent.trim()).join("").replace(/^\n/,"");Vo(t),this._devTools.showNotification(S.INFORMATION,"Environment information copied to clipboard",void 0,void 0,"versionInfoCopied")}};l([m({type:Object})],ue.prototype,"_devTools",void 0);l([w()],ue.prototype,"serverInfo",void 0);ue=l([k("vaadin-dev-tools-info")],ue);let Ce=class extends T{createRenderRoot(){return this}activate(){this._devTools.unreadErrors=!1,this.updateComplete.then(()=>{const e=this.renderRoot.querySelector(".message-tray .message:last-child");e&&e.scrollIntoView()})}render(){return c`<div class="message-tray">
      ${this._devTools.messages.map(e=>this._devTools.renderMessage(e))}
    </div>`}};l([m({type:Object})],Ce.prototype,"_devTools",void 0);Ce=l([k("vaadin-dev-tools-log")],Ce);const tt=16384;class ht extends ie{constructor(e){if(super(),!e)return;const t={transport:"websocket",url:e,contentType:"application/json; charset=UTF-8",reconnectInterval:5e3,timeout:-1,maxReconnectOnClose:1e7,trackMessageLength:!0,enableProtocol:!0,handleOnlineOffline:!1,executeCallbackBeforeReconnect:!0,messageDelimiter:"|",onMessage:o=>{const i={data:o.responseBody};this.handleMessage(i)},onError:o=>{this.handleError(o)}};Mo().then(o=>{this.socket=o.subscribe(t)})}onReload(){}onUpdate(e,t){}onMessage(e){}handleMessage(e){let t;try{t=JSON.parse(e.data)}catch(o){this.handleError(`[${o.name}: ${o.message}`);return}t.command==="hello"?(this.setStatus(u.ACTIVE),this.onHandshake()):t.command==="reload"?this.status===u.ACTIVE&&this.onReload():t.command==="update"?this.status===u.ACTIVE&&this.onUpdate(t.path,t.content):this.onMessage(t)}handleError(e){console.error(e),this.setStatus(u.ERROR),this.onConnectionError(e)}send(e,t){if(!this.socket){$e(()=>this.socket,r=>this.send(e,t));return}const o=JSON.stringify({command:e,data:t});let n=o.length+"|"+o;for(;n.length;)this.socket.push(n.substring(0,tt)),n=n.substring(tt)}}ht.HEARTBEAT_INTERVAL=18e4;function $e(s,e){const t=s();t?e(t):setTimeout(()=>$e(s,e),50)}function Mo(){return new Promise((s,e)=>{$e(()=>{var t;return(t=window==null?void 0:window.vaadinPush)==null?void 0:t.atmosphere},s)})}var f,S;(function(s){s.LOG="log",s.INFORMATION="information",s.WARNING="warning",s.ERROR="error"})(S||(S={}));let v=f=class extends T{static get styles(){return[y`
        :host {
          --dev-tools-font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen-Sans, Ubuntu, Cantarell,
            'Helvetica Neue', sans-serif;
          --dev-tools-font-family-monospace: SFMono-Regular, Menlo, Monaco, Consolas, 'Liberation Mono', 'Courier New',
            monospace;

          --dev-tools-font-size: 0.8125rem;
          --dev-tools-font-size-small: 0.75rem;

          --dev-tools-text-color: rgba(255, 255, 255, 0.8);
          --dev-tools-text-color-secondary: rgba(255, 255, 255, 0.65);
          --dev-tools-text-color-emphasis: rgba(255, 255, 255, 0.95);
          --dev-tools-text-color-active: rgba(255, 255, 255, 1);

          --dev-tools-background-color-inactive: rgba(45, 45, 45, 0.25);
          --dev-tools-background-color-active: rgba(45, 45, 45, 0.98);
          --dev-tools-background-color-active-blurred: rgba(45, 45, 45, 0.85);

          --dev-tools-border-radius: 0.5rem;
          --dev-tools-box-shadow: 0 0 0 1px rgba(255, 255, 255, 0.05), 0 4px 12px -2px rgba(0, 0, 0, 0.4);

          --dev-tools-blue-hsl: 206, 100%, 70%;
          --dev-tools-blue-color: hsl(var(--dev-tools-blue-hsl));
          --dev-tools-green-hsl: 145, 80%, 42%;
          --dev-tools-green-color: hsl(var(--dev-tools-green-hsl));
          --dev-tools-grey-hsl: 0, 0%, 50%;
          --dev-tools-grey-color: hsl(var(--dev-tools-grey-hsl));
          --dev-tools-yellow-hsl: 38, 98%, 64%;
          --dev-tools-yellow-color: hsl(var(--dev-tools-yellow-hsl));
          --dev-tools-red-hsl: 355, 100%, 68%;
          --dev-tools-red-color: hsl(var(--dev-tools-red-hsl));

          /* Needs to be in ms, used in JavaScript as well */
          --dev-tools-transition-duration: 180ms;

          all: initial;

          direction: ltr;
          cursor: default;
          font: normal 400 var(--dev-tools-font-size) / 1.125rem var(--dev-tools-font-family);
          color: var(--dev-tools-text-color);
          -webkit-user-select: none;
          -moz-user-select: none;
          user-select: none;
          color-scheme: dark;

          position: fixed;
          z-index: 20000;
          pointer-events: none;
          bottom: 0;
          right: 0;
          width: 100%;
          height: 100%;
          display: flex;
          flex-direction: column-reverse;
          align-items: flex-end;
        }

        .dev-tools {
          pointer-events: auto;
          display: flex;
          align-items: center;
          position: fixed;
          z-index: inherit;
          right: 0.5rem;
          bottom: 0.5rem;
          min-width: 1.75rem;
          height: 1.75rem;
          max-width: 1.75rem;
          border-radius: 0.5rem;
          padding: 0.375rem;
          box-sizing: border-box;
          background-color: var(--dev-tools-background-color-inactive);
          box-shadow: 0 0 0 1px rgba(255, 255, 255, 0.05);
          color: var(--dev-tools-text-color);
          transition: var(--dev-tools-transition-duration);
          white-space: nowrap;
          line-height: 1rem;
        }

        .dev-tools:hover,
        .dev-tools.active {
          background-color: var(--dev-tools-background-color-active);
          box-shadow: var(--dev-tools-box-shadow);
        }

        .dev-tools.active {
          max-width: calc(100% - 1rem);
        }

        .dev-tools .dev-tools-icon {
          flex: none;
          pointer-events: none;
          display: inline-block;
          width: 1rem;
          height: 1rem;
          fill: #fff;
          transition: var(--dev-tools-transition-duration);
          margin: 0;
        }

        .dev-tools.active .dev-tools-icon {
          opacity: 0;
          position: absolute;
          transform: scale(0.5);
        }

        .dev-tools .status-blip {
          flex: none;
          display: block;
          width: 6px;
          height: 6px;
          border-radius: 50%;
          z-index: 20001;
          background: var(--dev-tools-grey-color);
          position: absolute;
          top: -1px;
          right: -1px;
        }

        .dev-tools .status-description {
          overflow: hidden;
          text-overflow: ellipsis;
          padding: 0 0.25rem;
        }

        .dev-tools.error {
          background-color: hsla(var(--dev-tools-red-hsl), 0.15);
          animation: bounce 0.5s;
          animation-iteration-count: 2;
        }

        .switch {
          display: inline-flex;
          align-items: center;
        }

        .switch input {
          opacity: 0;
          width: 0;
          height: 0;
          position: absolute;
        }

        .switch .slider {
          display: block;
          flex: none;
          width: 28px;
          height: 18px;
          border-radius: 9px;
          background-color: rgba(255, 255, 255, 0.3);
          transition: var(--dev-tools-transition-duration);
          margin-right: 0.5rem;
        }

        .switch:focus-within .slider,
        .switch .slider:hover {
          background-color: rgba(255, 255, 255, 0.35);
          transition: none;
        }

        .switch input:focus-visible ~ .slider {
          box-shadow: 0 0 0 2px var(--dev-tools-background-color-active), 0 0 0 4px var(--dev-tools-blue-color);
        }

        .switch .slider::before {
          content: '';
          display: block;
          margin: 2px;
          width: 14px;
          height: 14px;
          background-color: #fff;
          transition: var(--dev-tools-transition-duration);
          border-radius: 50%;
        }

        .switch input:checked + .slider {
          background-color: var(--dev-tools-green-color);
        }

        .switch input:checked + .slider::before {
          transform: translateX(10px);
        }

        .switch input:disabled + .slider::before {
          background-color: var(--dev-tools-grey-color);
        }

        .window.hidden {
          opacity: 0;
          transform: scale(0);
          position: absolute;
        }

        .window.visible {
          transform: none;
          opacity: 1;
          pointer-events: auto;
        }

        .window.visible ~ .dev-tools {
          opacity: 0;
          pointer-events: none;
        }

        .window.visible ~ .dev-tools .dev-tools-icon,
        .window.visible ~ .dev-tools .status-blip {
          transition: none;
          opacity: 0;
        }

        .window {
          border-radius: var(--dev-tools-border-radius);
          overflow: auto;
          margin: 0.5rem;
          min-width: 30rem;
          max-width: calc(100% - 1rem);
          max-height: calc(100vh - 1rem);
          flex-shrink: 1;
          background-color: var(--dev-tools-background-color-active);
          color: var(--dev-tools-text-color);
          transition: var(--dev-tools-transition-duration);
          transform-origin: bottom right;
          display: flex;
          flex-direction: column;
          box-shadow: var(--dev-tools-box-shadow);
          outline: none;
        }

        .window-toolbar {
          display: flex;
          flex: none;
          align-items: center;
          padding: 0.375rem;
          white-space: nowrap;
          order: 1;
          background-color: rgba(0, 0, 0, 0.2);
          gap: 0.5rem;
        }

        .tab {
          color: var(--dev-tools-text-color-secondary);
          font: inherit;
          font-size: var(--dev-tools-font-size-small);
          font-weight: 500;
          line-height: 1;
          padding: 0.25rem 0.375rem;
          background: none;
          border: none;
          margin: 0;
          border-radius: 0.25rem;
          transition: var(--dev-tools-transition-duration);
        }

        .tab:hover,
        .tab.active {
          color: var(--dev-tools-text-color-active);
        }

        .tab.active {
          background-color: rgba(255, 255, 255, 0.12);
        }

        .tab.unreadErrors::after {
          content: '•';
          color: hsl(var(--dev-tools-red-hsl));
          font-size: 1.5rem;
          position: absolute;
          transform: translate(0, -50%);
        }

        .ahreflike {
          font-weight: 500;
          color: var(--dev-tools-text-color-secondary);
          text-decoration: underline;
          cursor: pointer;
        }

        .ahreflike:hover {
          color: var(--dev-tools-text-color-emphasis);
        }

        .button {
          all: initial;
          font-family: inherit;
          font-size: var(--dev-tools-font-size-small);
          line-height: 1;
          white-space: nowrap;
          background-color: rgba(0, 0, 0, 0.2);
          color: inherit;
          font-weight: 600;
          padding: 0.25rem 0.375rem;
          border-radius: 0.25rem;
        }

        .button:focus,
        .button:hover {
          color: var(--dev-tools-text-color-emphasis);
        }

        .minimize-button {
          flex: none;
          width: 1rem;
          height: 1rem;
          color: inherit;
          background-color: transparent;
          border: 0;
          padding: 0;
          margin: 0 0 0 auto;
          opacity: 0.8;
        }

        .minimize-button:hover {
          opacity: 1;
        }

        .minimize-button svg {
          max-width: 100%;
        }

        .message.information {
          --dev-tools-notification-color: var(--dev-tools-blue-color);
        }

        .message.warning {
          --dev-tools-notification-color: var(--dev-tools-yellow-color);
        }

        .message.error {
          --dev-tools-notification-color: var(--dev-tools-red-color);
        }

        .message {
          display: flex;
          padding: 0.1875rem 0.75rem 0.1875rem 2rem;
          background-clip: padding-box;
        }

        .message.log {
          padding-left: 0.75rem;
        }

        .message-content {
          margin-right: 0.5rem;
          -webkit-user-select: text;
          -moz-user-select: text;
          user-select: text;
        }

        .message-heading {
          position: relative;
          display: flex;
          align-items: center;
          margin: 0.125rem 0;
        }

        .message.log {
          color: var(--dev-tools-text-color-secondary);
        }

        .message:not(.log) .message-heading {
          font-weight: 500;
        }

        .message.has-details .message-heading {
          color: var(--dev-tools-text-color-emphasis);
          font-weight: 600;
        }

        .message-heading::before {
          position: absolute;
          margin-left: -1.5rem;
          display: inline-block;
          text-align: center;
          font-size: 0.875em;
          font-weight: 600;
          line-height: calc(1.25em - 2px);
          width: 14px;
          height: 14px;
          box-sizing: border-box;
          border: 1px solid transparent;
          border-radius: 50%;
        }

        .message.information .message-heading::before {
          content: 'i';
          border-color: currentColor;
          color: var(--dev-tools-notification-color);
        }

        .message.warning .message-heading::before,
        .message.error .message-heading::before {
          content: '!';
          color: var(--dev-tools-background-color-active);
          background-color: var(--dev-tools-notification-color);
        }

        .features-tray {
          padding: 0.75rem;
          flex: auto;
          overflow: auto;
          animation: fade-in var(--dev-tools-transition-duration) ease-in;
          user-select: text;
        }

        .features-tray p {
          margin-top: 0;
          color: var(--dev-tools-text-color-secondary);
        }

        .features-tray .feature {
          display: flex;
          align-items: center;
          gap: 1rem;
          padding-bottom: 0.5em;
        }

        .message .message-details {
          font-weight: 400;
          color: var(--dev-tools-text-color-secondary);
          margin: 0.25rem 0;
        }

        .message .message-details[hidden] {
          display: none;
        }

        .message .message-details p {
          display: inline;
          margin: 0;
          margin-right: 0.375em;
          word-break: break-word;
        }

        .message .persist {
          color: var(--dev-tools-text-color-secondary);
          white-space: nowrap;
          margin: 0.375rem 0;
          display: flex;
          align-items: center;
          position: relative;
          -webkit-user-select: none;
          -moz-user-select: none;
          user-select: none;
        }

        .message .persist::before {
          content: '';
          width: 1em;
          height: 1em;
          border-radius: 0.2em;
          margin-right: 0.375em;
          background-color: rgba(255, 255, 255, 0.3);
        }

        .message .persist:hover::before {
          background-color: rgba(255, 255, 255, 0.4);
        }

        .message .persist.on::before {
          background-color: rgba(255, 255, 255, 0.9);
        }

        .message .persist.on::after {
          content: '';
          order: -1;
          position: absolute;
          width: 0.75em;
          height: 0.25em;
          border: 2px solid var(--dev-tools-background-color-active);
          border-width: 0 0 2px 2px;
          transform: translate(0.05em, -0.05em) rotate(-45deg) scale(0.8, 0.9);
        }

        .message .dismiss-message {
          font-weight: 600;
          align-self: stretch;
          display: flex;
          align-items: center;
          padding: 0 0.25rem;
          margin-left: 0.5rem;
          color: var(--dev-tools-text-color-secondary);
        }

        .message .dismiss-message:hover {
          color: var(--dev-tools-text-color);
        }

        .notification-tray {
          display: flex;
          flex-direction: column-reverse;
          align-items: flex-end;
          margin: 0.5rem;
          flex: none;
        }

        .window.hidden + .notification-tray {
          margin-bottom: 3rem;
        }

        .notification-tray .message {
          pointer-events: auto;
          background-color: var(--dev-tools-background-color-active);
          color: var(--dev-tools-text-color);
          max-width: 30rem;
          box-sizing: border-box;
          border-radius: var(--dev-tools-border-radius);
          margin-top: 0.5rem;
          transition: var(--dev-tools-transition-duration);
          transform-origin: bottom right;
          animation: slideIn var(--dev-tools-transition-duration);
          box-shadow: var(--dev-tools-box-shadow);
          padding-top: 0.25rem;
          padding-bottom: 0.25rem;
        }

        .notification-tray .message.animate-out {
          animation: slideOut forwards var(--dev-tools-transition-duration);
        }

        .notification-tray .message .message-details {
          max-height: 10em;
          overflow: hidden;
        }

        .message-tray {
          flex: auto;
          overflow: auto;
          max-height: 20rem;
          user-select: text;
        }

        .message-tray .message {
          animation: fade-in var(--dev-tools-transition-duration) ease-in;
          padding-left: 2.25rem;
        }

        .message-tray .message.warning {
          background-color: hsla(var(--dev-tools-yellow-hsl), 0.09);
        }

        .message-tray .message.error {
          background-color: hsla(var(--dev-tools-red-hsl), 0.09);
        }

        .message-tray .message.error .message-heading {
          color: hsl(var(--dev-tools-red-hsl));
        }

        .message-tray .message.warning .message-heading {
          color: hsl(var(--dev-tools-yellow-hsl));
        }

        .message-tray .message + .message {
          border-top: 1px solid rgba(255, 255, 255, 0.07);
        }

        .message-tray .dismiss-message,
        .message-tray .persist {
          display: none;
        }

        .info-tray {
          padding: 0.75rem;
          position: relative;
          flex: auto;
          overflow: auto;
          animation: fade-in var(--dev-tools-transition-duration) ease-in;
          user-select: text;
        }

        .info-tray dl {
          margin: 0;
          display: grid;
          grid-template-columns: max-content 1fr;
          column-gap: 0.75rem;
          position: relative;
        }

        .info-tray dt {
          grid-column: 1;
          color: var(--dev-tools-text-color-emphasis);
        }

        .info-tray dt:not(:first-child)::before {
          content: '';
          width: 100%;
          position: absolute;
          height: 1px;
          background-color: rgba(255, 255, 255, 0.1);
          margin-top: -0.375rem;
        }

        .info-tray dd {
          grid-column: 2;
          margin: 0;
        }

        .info-tray :is(dt, dd):not(:last-child) {
          margin-bottom: 0.75rem;
        }

        .info-tray dd + dd {
          margin-top: -0.5rem;
        }

        .info-tray .live-reload-status::before {
          content: '•';
          color: var(--status-color);
          width: 0.75rem;
          display: inline-block;
          font-size: 1rem;
          line-height: 0.5rem;
        }

        .info-tray .copy {
          position: fixed;
          z-index: 1;
          top: 0.5rem;
          right: 0.5rem;
        }

        .info-tray .switch {
          vertical-align: -4px;
        }

        @keyframes slideIn {
          from {
            transform: translateX(100%);
            opacity: 0;
          }
          to {
            transform: translateX(0%);
            opacity: 1;
          }
        }

        @keyframes slideOut {
          from {
            transform: translateX(0%);
            opacity: 1;
          }
          to {
            transform: translateX(100%);
            opacity: 0;
          }
        }

        @keyframes fade-in {
          0% {
            opacity: 0;
          }
        }

        @keyframes bounce {
          0% {
            transform: scale(0.8);
          }
          50% {
            transform: scale(1.5);
            background-color: hsla(var(--dev-tools-red-hsl), 1);
          }
          100% {
            transform: scale(1);
          }
        }

        @supports (backdrop-filter: blur(1px)) {
          .dev-tools,
          .window,
          .notification-tray .message {
            backdrop-filter: blur(8px);
          }
          .dev-tools:hover,
          .dev-tools.active,
          .window,
          .notification-tray .message {
            background-color: var(--dev-tools-background-color-active-blurred);
          }
        }
      `,nt]}static get isActive(){const e=window.sessionStorage.getItem(f.ACTIVE_KEY_IN_SESSION_STORAGE);return e===null||e!=="false"}static notificationDismissed(e){const t=window.localStorage.getItem(f.DISMISSED_NOTIFICATIONS_IN_LOCAL_STORAGE);return t!==null&&t.includes(e)}elementTelemetry(){let e={};try{const t=localStorage.getItem("vaadin.statistics.basket");if(!t)return;e=JSON.parse(t)}catch{return}this.frontendConnection&&this.frontendConnection.send("reportTelemetry",{browserData:e})}openWebSocketConnection(){if(this.frontendStatus=u.UNAVAILABLE,this.javaStatus=u.UNAVAILABLE,!this.conf.token){console.error("Dev tools functionality denied for this host.");return}const e=n=>this.log(S.ERROR,n),t=()=>{this.showSplashMessage("Reloading…");const n=window.sessionStorage.getItem(f.TRIGGERED_COUNT_KEY_IN_SESSION_STORAGE),r=n?parseInt(n,10)+1:1;window.sessionStorage.setItem(f.TRIGGERED_COUNT_KEY_IN_SESSION_STORAGE,r.toString()),window.sessionStorage.setItem(f.TRIGGERED_KEY_IN_SESSION_STORAGE,"true"),window.location.reload()},o=(n,r)=>{let a=document.head.querySelector(`style[data-file-path='${n}']`);a?(this.log(S.INFORMATION,"Hot update of "+n),a.textContent=r,document.dispatchEvent(new CustomEvent("vaadin-theme-updated"))):t()},i=new ht(this.getDedicatedWebSocketUrl());i.onHandshake=()=>{this.log(S.LOG,"Vaadin development mode initialized"),f.isActive||i.setActive(!1),this.elementTelemetry()},i.onConnectionError=e,i.onReload=t,i.onUpdate=o,i.onStatusChange=n=>{this.frontendStatus=n},i.onMessage=n=>this.handleFrontendMessage(n),this.frontendConnection=i,this.conf.backend===f.SPRING_BOOT_DEVTOOLS&&(this.javaConnection=new xt(this.getSpringBootWebSocketUrl(window.location)),this.javaConnection.onHandshake=()=>{f.isActive||this.javaConnection.setActive(!1)},this.javaConnection.onReload=t,this.javaConnection.onConnectionError=e,this.javaConnection.onStatusChange=n=>{this.javaStatus=n},this.javaConnection.onHandshake=()=>{this.conf.backend&&this.log(S.INFORMATION,`Java live reload available: ${f.BACKEND_DISPLAY_NAME[this.conf.backend]}`)}),this.conf.backend||this.showNotification(S.WARNING,"Java live reload unavailable","Live reload for Java changes is currently not set up. Find out how to make use of this functionality to boost your workflow.","https://vaadin.com/docs/latest/flow/configuration/live-reload","liveReloadUnavailable")}tabHandleMessage(e,t){const o=e;return o.handleMessage&&o.handleMessage.call(e,t)}handleFrontendMessage(e){for(const t of this.tabs)if(t.element&&this.tabHandleMessage(t.element,e))return;if(e.command==="featureFlags")this.features=e.data.features;else if(e.command==="themeEditorState"){const t=!!window.Vaadin.Flow;this.themeEditorState=e.data,t&&this.themeEditorState!==j.disabled&&(this.tabs.push({id:"theme-editor",title:"Theme Editor (Preview)",render:()=>this.renderThemeEditor()}),this.requestUpdate())}else yt(e)||this.unhandledMessages.push(e)}getDedicatedWebSocketUrl(){function e(o){const i=document.createElement("div");return i.innerHTML=`<a href="${o}"/>`,i.firstChild.href}if(this.conf.url===void 0)return;const t=e(this.conf.url);if(!t.startsWith("http://")&&!t.startsWith("https://")){console.error("The protocol of the url should be http or https for live reload to work.");return}return`${t}?v-r=push&debug_window&token=${this.conf.token}`}getSpringBootWebSocketUrl(e){const{hostname:t}=e,o=e.protocol==="https:"?"wss":"ws";if(t.endsWith("gitpod.io")){const i=t.replace(/.*?-/,"");return`${o}://${this.conf.liveReloadPort}-${i}`}else return`${o}://${t}:${this.conf.liveReloadPort}`}constructor(){super(),this.unhandledMessages=[],this.conf={enable:!1,url:"",liveReloadPort:-1},this.expanded=!1,this.messages=[],this.notifications=[],this.frontendStatus=u.UNAVAILABLE,this.javaStatus=u.UNAVAILABLE,this.tabs=[{id:"log",title:"Log",render:"vaadin-dev-tools-log"},{id:"info",title:"Info",render:"vaadin-dev-tools-info"},{id:"features",title:"Feature Flags",render:()=>this.renderFeatures()}],this.activeTab="log",this.features=[],this.unreadErrors=!1,this.componentPickActive=!1,this.themeEditorState=j.disabled,this.nextMessageId=1,this.transitionDuration=0,window.Vaadin.Flow&&this.tabs.push({id:"code",title:"Code",render:()=>this.renderCode()})}connectedCallback(){if(super.connectedCallback(),this.catchErrors(),this.conf=window.Vaadin.devToolsConf||this.conf,this.disableEventListener=i=>this.demoteSplashMessage(),document.body.addEventListener("focus",this.disableEventListener),document.body.addEventListener("click",this.disableEventListener),window.sessionStorage.getItem(f.TRIGGERED_KEY_IN_SESSION_STORAGE)){const i=new Date,n=`${`0${i.getHours()}`.slice(-2)}:${`0${i.getMinutes()}`.slice(-2)}:${`0${i.getSeconds()}`.slice(-2)}`;this.showSplashMessage(`Page reloaded at ${n}`),window.sessionStorage.removeItem(f.TRIGGERED_KEY_IN_SESSION_STORAGE)}this.transitionDuration=parseInt(window.getComputedStyle(this).getPropertyValue("--dev-tools-transition-duration"),10);const t=window;t.Vaadin=t.Vaadin||{},t.Vaadin.devTools=Object.assign(this,t.Vaadin.devTools),document.documentElement.addEventListener("vaadin-overlay-outside-click",i=>{const n=i,r=n.target.owner;if(r?_t(this,r):!1)return;n.detail.sourceEvent.composedPath().includes(this)&&i.preventDefault()});const o=window.Vaadin;o.devToolsPlugins&&(Array.from(o.devToolsPlugins).forEach(i=>this.initPlugin(i)),o.devToolsPlugins={push:i=>this.initPlugin(i)}),this.openWebSocketConnection(),bt()}async initPlugin(e){const t=this;e.init({addTab:(o,i)=>{t.tabs.push({id:o,title:o,render:i})},send:function(o,i){t.frontendConnection.send(o,i)}})}format(e){return e.toString()}catchErrors(){const e=window.Vaadin.ConsoleErrors;e&&e.forEach(t=>{this.log(S.ERROR,t.map(o=>this.format(o)).join(" "))}),window.Vaadin.ConsoleErrors={push:t=>{this.log(S.ERROR,t.map(o=>this.format(o)).join(" "))}}}disconnectedCallback(){this.disableEventListener&&(document.body.removeEventListener("focus",this.disableEventListener),document.body.removeEventListener("click",this.disableEventListener)),super.disconnectedCallback()}toggleExpanded(){this.notifications.slice().forEach(e=>this.dismissNotification(e.id)),this.expanded=!this.expanded,this.expanded&&this.root.focus()}showSplashMessage(e){this.splashMessage=e,this.splashMessage&&(this.expanded?this.demoteSplashMessage():setTimeout(()=>{this.demoteSplashMessage()},f.AUTO_DEMOTE_NOTIFICATION_DELAY))}demoteSplashMessage(){this.splashMessage&&this.log(S.LOG,this.splashMessage),this.showSplashMessage(void 0)}checkLicense(e){this.frontendConnection?this.frontendConnection.send("checkLicense",e):it({message:"Internal error: no connection",product:e})}log(e,t,o,i,n){const r=this.nextMessageId;for(this.nextMessageId+=1,this.messages.push({id:r,type:e,message:t,details:o,link:i,dontShowAgain:!1,dontShowAgainMessage:n,deleted:!1});this.messages.length>f.MAX_LOG_ROWS;)this.messages.shift();this.requestUpdate(),this.updateComplete.then(()=>{const a=this.renderRoot.querySelector(".message-tray .message:last-child");this.expanded&&a?(setTimeout(()=>a.scrollIntoView({behavior:"smooth"}),this.transitionDuration),this.unreadErrors=!1):e===S.ERROR&&(this.unreadErrors=!0)})}showNotification(e,t,o,i,n,r){if(n===void 0||!f.notificationDismissed(n)){if(this.notifications.filter(p=>p.persistentId===n).filter(p=>!p.deleted).length>0)return;const h=this.nextMessageId;this.nextMessageId+=1,this.notifications.push({id:h,type:e,message:t,details:o,link:i,persistentId:n,dontShowAgain:!1,dontShowAgainMessage:r,deleted:!1}),i===void 0&&setTimeout(()=>{this.dismissNotification(h)},f.AUTO_DEMOTE_NOTIFICATION_DELAY),this.requestUpdate()}else this.log(e,t,o,i)}dismissNotification(e){const t=this.findNotificationIndex(e);if(t!==-1&&!this.notifications[t].deleted){const o=this.notifications[t];if(o.dontShowAgain&&o.persistentId&&!f.notificationDismissed(o.persistentId)){let i=window.localStorage.getItem(f.DISMISSED_NOTIFICATIONS_IN_LOCAL_STORAGE);i=i===null?o.persistentId:`${i},${o.persistentId}`,window.localStorage.setItem(f.DISMISSED_NOTIFICATIONS_IN_LOCAL_STORAGE,i)}o.deleted=!0,this.log(o.type,o.message,o.details,o.link),setTimeout(()=>{const i=this.findNotificationIndex(e);i!==-1&&(this.notifications.splice(i,1),this.requestUpdate())},this.transitionDuration)}}findNotificationIndex(e){let t=-1;return this.notifications.some((o,i)=>o.id===e?(t=i,!0):!1),t}toggleDontShowAgain(e){const t=this.findNotificationIndex(e);if(t!==-1&&!this.notifications[t].deleted){const o=this.notifications[t];o.dontShowAgain=!o.dontShowAgain,this.requestUpdate()}}setActive(e){var t,o;(t=this.frontendConnection)==null||t.setActive(e),(o=this.javaConnection)==null||o.setActive(e),window.sessionStorage.setItem(f.ACTIVE_KEY_IN_SESSION_STORAGE,e?"true":"false")}getStatusColor(e){return e===u.ACTIVE?"var(--dev-tools-green-color)":e===u.INACTIVE?"var(--dev-tools-grey-color)":e===u.UNAVAILABLE?"var(--dev-tools-yellow-hsl)":e===u.ERROR?"var(--dev-tools-red-color)":"none"}renderMessage(e){return c`
      <div
        class="message ${e.type} ${e.deleted?"animate-out":""} ${e.details||e.link?"has-details":""}"
      >
        <div class="message-content">
          <div class="message-heading">${e.message}</div>
          <div class="message-details" ?hidden="${!e.details&&!e.link}">
            ${e.details?c`<p>${e.details}</p>`:""}
            ${e.link?c`<a class="ahreflike" href="${e.link}" target="_blank">Learn more</a>`:""}
          </div>
          ${e.persistentId?c`<div
                class="persist ${e.dontShowAgain?"on":"off"}"
                @click=${()=>this.toggleDontShowAgain(e.id)}
              >
                ${e.dontShowAgainMessage||"Don’t show again"}
              </div>`:""}
        </div>
        <div class="dismiss-message" @click=${()=>this.dismissNotification(e.id)}>Dismiss</div>
      </div>
    `}render(){return c` <div
        class="window ${this.expanded&&!this.componentPickActive?"visible":"hidden"}"
        tabindex="0"
        @keydown=${e=>e.key==="Escape"&&this.expanded&&this.toggleExpanded()}
      >
        <div class="window-toolbar">
          ${this.tabs.map(e=>c`<button
                class=${ot({tab:!0,active:this.activeTab===e.id,unreadErrors:e.id==="log"&&this.unreadErrors})}
                id="${e.id}"
                @click=${()=>{const t=this.tabs.find(n=>n.id===this.activeTab);if(t&&t.element){const n=typeof t.render=="function"?t.element.firstElementChild:t.element,r=n==null?void 0:n.deactivate;r&&r.call(n)}this.activeTab=e.id;const o=typeof e.render=="function"?e.element.firstElementChild:e.element,i=o.activate;i&&i.call(o)}}
              >
                ${e.title}
              </button> `)}
          <button class="minimize-button" title="Minimize" @click=${()=>this.toggleExpanded()}>
            <svg fill="none" height="16" viewBox="0 0 16 16" width="16" xmlns="http://www.w3.org/2000/svg">
              <g fill="#fff" opacity=".8">
                <path
                  d="m7.25 1.75c0-.41421.33579-.75.75-.75h3.25c2.0711 0 3.75 1.67893 3.75 3.75v6.5c0 2.0711-1.6789 3.75-3.75 3.75h-6.5c-2.07107 0-3.75-1.6789-3.75-3.75v-3.25c0-.41421.33579-.75.75-.75s.75.33579.75.75v3.25c0 1.2426 1.00736 2.25 2.25 2.25h6.5c1.2426 0 2.25-1.0074 2.25-2.25v-6.5c0-1.24264-1.0074-2.25-2.25-2.25h-3.25c-.41421 0-.75-.33579-.75-.75z"
                />
                <path
                  d="m2.96967 2.96967c.29289-.29289.76777-.29289 1.06066 0l5.46967 5.46967v-2.68934c0-.41421.33579-.75.75-.75.4142 0 .75.33579.75.75v4.5c0 .4142-.3358.75-.75.75h-4.5c-.41421 0-.75-.3358-.75-.75 0-.41421.33579-.75.75-.75h2.68934l-5.46967-5.46967c-.29289-.29289-.29289-.76777 0-1.06066z"
                />
              </g>
            </svg>
          </button>
        </div>
        <div id="tabContainer"></div>
      </div>

      <div class="notification-tray">${this.notifications.map(e=>this.renderMessage(e))}</div>
      <vaadin-dev-tools-component-picker
        .active=${this.componentPickActive}
        @component-picker-opened=${()=>{this.componentPickActive=!0}}
        @component-picker-closed=${()=>{this.componentPickActive=!1}}
      ></vaadin-dev-tools-component-picker>
      <div
        style="display: var(--dev-tools-button-display, 'block')"
        class="dev-tools ${this.splashMessage?"active":""}${this.unreadErrors?" error":""}"
        @click=${()=>this.toggleExpanded()}
      >
        ${this.unreadErrors?c`<svg
              fill="none"
              height="16"
              viewBox="0 0 16 16"
              width="16"
              xmlns="http://www.w3.org/2000/svg"
              xmlns:xlink="http://www.w3.org/1999/xlink"
              class="dev-tools-icon error"
            >
              <clipPath id="a"><path d="m0 0h16v16h-16z" /></clipPath>
              <g clip-path="url(#a)">
                <path
                  d="m6.25685 2.09894c.76461-1.359306 2.72169-1.359308 3.4863 0l5.58035 9.92056c.7499 1.3332-.2135 2.9805-1.7432 2.9805h-11.1606c-1.529658 0-2.4930857-1.6473-1.743156-2.9805z"
                  fill="#ff5c69"
                />
                <path
                  d="m7.99699 4c-.45693 0-.82368.37726-.81077.834l.09533 3.37352c.01094.38726.32803.69551.71544.69551.38741 0 .70449-.30825.71544-.69551l.09533-3.37352c.0129-.45674-.35384-.834-.81077-.834zm.00301 8c.60843 0 1-.3879 1-.979 0-.5972-.39157-.9851-1-.9851s-1 .3879-1 .9851c0 .5911.39157.979 1 .979z"
                  fill="#fff"
                />
              </g>
            </svg>`:c`<svg
              fill="none"
              height="17"
              viewBox="0 0 16 17"
              width="16"
              xmlns="http://www.w3.org/2000/svg"
              class="dev-tools-icon logo"
            >
              <g fill="#fff">
                <path
                  d="m8.88273 5.97926c0 .04401-.0032.08898-.00801.12913-.02467.42848-.37813.76767-.8117.76767-.43358 0-.78704-.34112-.81171-.76928-.00481-.04015-.00801-.08351-.00801-.12752 0-.42784-.10255-.87656-1.14434-.87656h-3.48364c-1.57118 0-2.315271-.72849-2.315271-2.21758v-1.26683c0-.42431.324618-.768314.748261-.768314.42331 0 .74441.344004.74441.768314v.42784c0 .47924.39576.81265 1.11293.81265h3.41538c1.5542 0 1.67373 1.156 1.725 1.7679h.03429c.05095-.6119.17048-1.7679 1.72468-1.7679h3.4154c.7172 0 1.0145-.32924 1.0145-.80847l-.0067-.43202c0-.42431.3227-.768314.7463-.768314.4234 0 .7255.344004.7255.768314v1.26683c0 1.48909-.6181 2.21758-2.1893 2.21758h-3.4836c-1.04182 0-1.14437.44872-1.14437.87656z"
                />
                <path
                  d="m8.82577 15.1648c-.14311.3144-.4588.5335-.82635.5335-.37268 0-.69252-.2249-.83244-.5466-.00206-.0037-.00412-.0073-.00617-.0108-.00275-.0047-.00549-.0094-.00824-.0145l-3.16998-5.87318c-.08773-.15366-.13383-.32816-.13383-.50395 0-.56168.45592-1.01879 1.01621-1.01879.45048 0 .75656.22069.96595.6993l2.16882 4.05042 2.17166-4.05524c.2069-.47379.513-.69448.9634-.69448.5603 0 1.0166.45711 1.0166 1.01879 0 .17579-.0465.35029-.1348.50523l-3.1697 5.8725c-.00503.0096-.01006.0184-.01509.0272-.00201.0036-.00402.0071-.00604.0106z"
                />
              </g>
            </svg>`}

        <span
          class="status-blip"
          style="background: linear-gradient(to right, ${this.getStatusColor(this.frontendStatus)} 50%, ${this.getStatusColor(this.javaStatus)} 50%)"
        ></span>
        ${this.splashMessage?c`<span class="status-description">${this.splashMessage}</span></div>`:pt}
      </div>`}updated(e){var n;super.updated(e);const t=this.renderRoot.querySelector("#tabContainer"),o=[];if(this.tabs.forEach(r=>{r.element||(typeof r.render=="function"?r.element=document.createElement("div"):(r.element=document.createElement(r.render),r.element._devTools=this),o.push(r.element))}),(t==null?void 0:t.childElementCount)!==this.tabs.length){for(let r=0;r<this.tabs.length;r++){const a=this.tabs[r];t.childElementCount>r&&t.children[r]===a.element||t.insertBefore(a.element,t.children[r])}for(;(t==null?void 0:t.childElementCount)>this.tabs.length;)(n=t.lastElementChild)==null||n.remove()}for(const r of this.tabs){typeof r.render=="function"?Se(r.render(),r.element):r.element.requestUpdate&&r.element.requestUpdate();const a=r.id===this.activeTab;r.element.hidden=!a}for(const r of o)for(var i=0;i<this.unhandledMessages.length;i++)this.tabHandleMessage(r,this.unhandledMessages[i])&&(this.unhandledMessages.splice(i,1),i--)}renderCode(){return c`<div class="info-tray">
      <div>
        <select id="locationType">
          <option value="create" selected>Create</option>
          <option value="attach">Attach</option>
        </select>
        <button
          class="button pick"
          @click=${async()=>{await d(()=>Promise.resolve().then(()=>To),void 0),this.componentPicker.open({infoTemplate:c`
                <div>
                  <h3>Locate a component in source code</h3>
                  <p>Use the mouse cursor to highlight components in the UI.</p>
                  <p>Use arrow down/up to cycle through and highlight specific components under the cursor.</p>
                  <p>
                    Click the primary mouse button to open the corresponding source code line of the highlighted
                    component in your IDE.
                  </p>
                </div>
              `,pickCallback:e=>{const t={nodeId:e.nodeId,uiId:e.uiId};this.renderRoot.querySelector("#locationType").value==="create"?this.frontendConnection.send("showComponentCreateLocation",t):this.frontendConnection.send("showComponentAttachLocation",t)}})}}
        >
          Find component in code
        </button>
      </div>
      </div>
    </div>`}renderFeatures(){return c`<div class="features-tray">
      ${this.features.map(e=>c`<div class="feature">
          <label class="switch">
            <input
              class="feature-toggle"
              id="feature-toggle-${e.id}"
              type="checkbox"
              ?checked=${e.enabled}
              @change=${t=>this.toggleFeatureFlag(t,e)}
            />
            <span class="slider"></span>
            ${e.title}
          </label>
          <a class="ahreflike" href="${e.moreInfoLink}" target="_blank">Learn more</a>
        </div>`)}
    </div>`}setJavaLiveReloadActive(e){var t;this.javaConnection?this.javaConnection.setActive(e):(t=this.frontendConnection)==null||t.setActive(e)}renderThemeEditor(){return c` <vaadin-dev-tools-theme-editor
      .expanded=${this.expanded}
      .themeEditorState=${this.themeEditorState}
      .pickerProvider=${()=>this.componentPicker}
      .connection=${this.frontendConnection}
      @before-open=${()=>this.setJavaLiveReloadActive(!1)}
      @after-close=${()=>this.setJavaLiveReloadActive(!0)}
    ></vaadin-dev-tools-theme-editor>`}toggleFeatureFlag(e,t){const o=e.target.checked;this.frontendConnection?(this.frontendConnection.send("setFeature",{featureId:t.id,enabled:o}),this.showNotification(S.INFORMATION,`“${t.title}” ${o?"enabled":"disabled"}`,t.requiresServerRestart?"This feature requires a server restart":void 0,void 0,`feature${t.id}${o?"Enabled":"Disabled"}`)):this.log(S.ERROR,`Unable to toggle feature ${t.title}: No server connection available`)}};v.MAX_LOG_ROWS=1e3;v.DISMISSED_NOTIFICATIONS_IN_LOCAL_STORAGE="vaadin.live-reload.dismissedNotifications";v.ACTIVE_KEY_IN_SESSION_STORAGE="vaadin.live-reload.active";v.TRIGGERED_KEY_IN_SESSION_STORAGE="vaadin.live-reload.triggered";v.TRIGGERED_COUNT_KEY_IN_SESSION_STORAGE="vaadin.live-reload.triggeredCount";v.AUTO_DEMOTE_NOTIFICATION_DELAY=5e3;v.HOTSWAP_AGENT="HOTSWAP_AGENT";v.JREBEL="JREBEL";v.SPRING_BOOT_DEVTOOLS="SPRING_BOOT_DEVTOOLS";v.BACKEND_DISPLAY_NAME={HOTSWAP_AGENT:"HotswapAgent",JREBEL:"JRebel",SPRING_BOOT_DEVTOOLS:"Spring Boot Devtools"};l([m({type:Boolean,attribute:!1})],v.prototype,"expanded",void 0);l([m({type:Array,attribute:!1})],v.prototype,"messages",void 0);l([m({type:String,attribute:!1})],v.prototype,"splashMessage",void 0);l([m({type:Array,attribute:!1})],v.prototype,"notifications",void 0);l([m({type:String,attribute:!1})],v.prototype,"frontendStatus",void 0);l([m({type:String,attribute:!1})],v.prototype,"javaStatus",void 0);l([w()],v.prototype,"tabs",void 0);l([w()],v.prototype,"activeTab",void 0);l([w()],v.prototype,"features",void 0);l([w()],v.prototype,"unreadErrors",void 0);l([Q(".window")],v.prototype,"root",void 0);l([Q("vaadin-dev-tools-component-picker")],v.prototype,"componentPicker",void 0);l([w()],v.prototype,"componentPickActive",void 0);l([w()],v.prototype,"themeEditorState",void 0);v=f=l([k("vaadin-dev-tools")],v);export{b as E,St as a,Ro as b,Tt as c,At as d,No as e,Ct as f,ss as g,Io as h,_e as i,is as j,N as p,I as s,O as t};
