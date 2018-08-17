<#setting locale="en_US">
UNA:+.? '
UNH+${order.number}+${order.status}+${order.creationDate?string["yyyy-MM-dd"]}'
CTA+${supplier.name}+${supplier.phoneNumber}'
<#list items as item>
LIN+${item.quantity}+${item.code}+${item.price}'
</#list>