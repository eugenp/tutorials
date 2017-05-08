// Avoid ambiguities due to template naming (and hence the generated class name)!
def inv = this.model.invoice

yieldUnescaped '<!DOCTYPE html>'

html(lang: 'en') {

  head {
    title("Invoice #${inv.id}")
    meta(charset: 'utf-8')
    meta('http-equiv': 'X-UA-Compatible', content: 'IE=edge')
    meta(name: 'viewport', content: 'width=device-width, initial-scale=1, minimum-scale=1, shrink-to-fit=no')
    link(href: '../../resources/invoice.css', rel: 'stylesheet')
  }

  body {

    header {
      h1('INVOICE')
      h2('Baeldung – The Spring Resource')
    }

    section {
      dl {
        dt('Invoice')
        dd("TPL#${inv.id}")
        dt('Date')
        dd("${inv.date}")
      }
    }

    if (inv.customer) {
      section {
        dl {
          dt('BILL TO')
          dd("${inv.customer.firstName} ${inv.customer.lastName}")
          if (inv.customer.vatNumber) {
            dt('VAT')
            dd("${inv.customer.vatNumber}")
          }
        }
      }
    }

    table {
      thead {
        tr {
          th('Item')
          th('Quantity')
          th('Unit Price (€)')
          th('VAT (%)')
          th('Line Total')
        }
      }

      tbody {
        inv.items.each { item ->
          tr {
            td("${item.name}")
            td("${item.quantity}")
            td("${item.unitPrice}")
            td("${item.vat}")
            td("${item.totalPrice}")
          }
        }
      }

      tfoot {
        tr {
          td(colspan: 3, ' ')
          td { strong('Total') }
          td("€${inv.totalPrice}")
        }
      }
    }

    footer {
      p { em('Baeldung – The Spring Resource | Java, Spring and Web Development tutorials') }
      p {
        a(href: 'http://baeldung.com', 'baeldung.com')
        yieldUnescaped ' | '
        a(href: 'mailto:email@baeldung.com', 'email@baeldung.com')
      }
    }

    // script(src: '../../resources/invoice.js')
  }

}
