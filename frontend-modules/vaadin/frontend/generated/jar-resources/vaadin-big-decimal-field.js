/*
 * Copyright 2000-2023 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
(function () {
  let memoizedTemplate;

  customElements.whenDefined('vaadin-text-field').then(() => {
    class BigDecimalFieldElement extends customElements.get('vaadin-text-field') {
      static get template() {
        if (!memoizedTemplate) {
          memoizedTemplate = super.template.cloneNode(true);
          memoizedTemplate.innerHTML += `<style>
                  :host {
                    width: 8em;
                  }

                  :host([dir="rtl"]) [part="input-field"] {
                    direction: ltr;
                  }

                  :host([dir="rtl"]) [part="input-field"] ::slotted(input) {
                    --_lumo-text-field-overflow-mask-image: linear-gradient(to left, transparent, #000 1.25em) !important;
                  }
            </style>`;
        }
        return memoizedTemplate;
      }

      static get is() {
        return 'vaadin-big-decimal-field';
      }

      static get properties() {
        return {
          _decimalSeparator: {
            type: String,
            value: '.',
            observer: '__decimalSeparatorChanged'
          }
        };
      }

      ready() {
        super.ready();
        this.inputElement.setAttribute('inputmode', 'decimal');
      }

      __decimalSeparatorChanged(separator, oldSeparator) {
        this.allowedCharPattern = '[-+\\d' + separator + ']';

        if (this.value && oldSeparator) {
          this.value = this.value.split(oldSeparator).join(separator);
        }
      }
    }

    customElements.define(BigDecimalFieldElement.is, BigDecimalFieldElement);
  });
})();
