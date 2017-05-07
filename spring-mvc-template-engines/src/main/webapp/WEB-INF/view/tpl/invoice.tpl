// Avoid ambiguities due to template naming (hence generated class name)!
def inv = this.model.invoice
try {

    yieldUnescaped '<!DOCTYPE html>'
    html(lang: 'en') {

        head {
            title("Invoice #${inv.id}")
            meta('charset': '"utf-8"')
            meta('http-equiv': '"X-UA-Compatible"', 'content': '"IE=edge"')
            meta('name': '"viewport"', 'content': '"width=device-width, initial-scale=1, minimum-scale=1, shrink-to-fit=no"')
            link('href': '"../../resources/invoice.css"', 'rel': '"stylesheet"')
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

            yieldUnescaped "$inv.id"
        }

    }

} catch (e) {
    yieldUnescaped e.message
}
