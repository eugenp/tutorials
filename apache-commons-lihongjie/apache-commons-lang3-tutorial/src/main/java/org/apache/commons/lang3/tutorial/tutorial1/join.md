需求： 

在平时开发过程中有时遇到这样： 字符串使用逗号分隔.
比如： "abcdefg"  输出 "a,b,c,d,e,f,g"
常用的做法是 for循环拼接:
StringBuilder sb = new StringBuilder();
char[] chars = str.toCharArray();
for (int i = 0; i < chars.length; i++) {
	sb.append(char[i]);
	sb.append(',');	
}
return sb.toString();

这样有个问题会在最后多一个',',还是空字符串的时候没有处理，容易出现空指针异常。
然后继续 使用 str.subString(0, str.length - 1); 来去掉最后一个逗号 ',';这样做效率不高而且代码也复杂。

使用 StringUtils.join() 方法解决，看源代码中的处理方式。