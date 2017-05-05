// Avoid ambiguities due to template naming (hence generated class name)!
def inv = this.model.invoice

yieldUnescaped '<!DOCTYPE html>'
html(lang: 'en') {

    head {
        title("Invoice #${invoice.id}")
        meta('charset': '"utf-8"')
        meta('http-equiv': '"X-UA-Compatible"', 'content': '"IE=edge"')
        meta('name': '"viewport"', 'content': '"width=device-width, initial-scale=1, minimum-scale=1, shrink-to-fit=no"')
        link('href': '"../../../resources/invoice.css"', 'rel': '"stylesheet"')
    }

    body {
        yieldUnescaped "$inv.id"
    }

}
