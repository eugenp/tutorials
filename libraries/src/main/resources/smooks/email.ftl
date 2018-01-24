Hi,
Order number #${order.number} created on ${order.creationDate?date} is currently in ${order.status} status.
Consider contact supplier "${supplier.name}"  with phone number: "${supplier.phoneNumber}".
Order items:
<#list items as item>
${item.quantity}  X ${item.code} (total price ${item.price * item.quantity})
</#list>