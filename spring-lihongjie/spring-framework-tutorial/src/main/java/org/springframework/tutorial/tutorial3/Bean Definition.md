The objects that form the backbone of your application and that are managed by the Spring IoC container are called beans. A bean is an object that is instantiated, assembled, and otherwise managed by a Spring IoC container. These beans are created with the configuration metadata that you supply to the container, for example, in the form of XML <bean/> definitions which you have already seen in previous chapters.

The bean definition contains the information called configuration metadata which is needed for the container to know the followings:

How to create a bean

Bean's lifecycle details

Bean's dependencies

All the above configuration metadata translates into a set of the following properties that make up each bean definition.

<table>
<tr>
<th>Properties</th>
<th>Description</th>
</tr>
<tr>
<td>class</td>
<td>This attribute is mandatory and specify the bean class to be used to create the bean.</td>
</tr>
<tr>
<th>name</th>
<th>This attribute specifies the bean identifier uniquely. In XML-based configuration metadata, you use the id and/or name attributes to specify the bean identifier(s).</th>
</tr>
<tr>
<th>scope</th>
<th>This attribute specifies the scope of the objects created from a particular bean definition and it will be discussed in bean scopes chapter.</th>
</tr>
<tr>
<th>constructor-arg</th>
<th>This is used to inject the dependencies and will be discussed in next chapters.</th>
</tr>
<tr>
<th>properties</th>
<th>This is used to inject the dependencies and will be discussed in next chapters.</th>
</tr>
<tr>
<th>autowiring mode</th>
<th>This is used to inject the dependencies and will be discussed in next chapters.</th>
</tr>
<tr>
<th>lazy-initialization mode</th>
<th>A lazy-initialized bean tells the IoC container to create a bean instance when it is first requested, rather than at startup.</th>
</tr>
<tr>
<th>initialization method	</th>
<th>A callback to be called just after all necessary properties on the bean have been set by the container. It will be discussed in bean life cycle chapter.</th>
</tr>
<tr>
<th>destruction method</th>
<th>A callback to be used when the container containing the bean is destroyed. It will be discussed in bean life cycle chapter.</th>
</tr>
</table>

