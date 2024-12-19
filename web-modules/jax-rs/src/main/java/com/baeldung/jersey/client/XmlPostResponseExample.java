public class XmlResponseExample {
    public static void main(String[] args) throws JAXBException {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://api.example.com/product");
        String xmlPayload = "<product><id>1</id></product>";
        Response response = target.request(MediaType.APPLICATION_XML).post(Entity.entity(xmlPayload, MediaType.APPLICATION_XML));
        try {
            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                JAXBContext jaxbContext = JAXBContext.newInstance(Product.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                Product product = (Product) unmarshaller.unmarshal(response.readEntity(InputStream.class));
                System.out.println("Product Name: " + product.getName());
            } else {
                System.err.println("Failed to get product data");
            }
        } finally {
            response.close();
            client.close();
        }
    }
}