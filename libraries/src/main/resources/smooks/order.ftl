UNA:+.? '
UNH+${order.number}+${order.status}+${order.creationDate?date}'
CTA+${supplier.name}+${supplier.phoneNumber}'
<#list items as item>
LIN+${item.quantity}+${item.code}+${item.price}'
</#list>