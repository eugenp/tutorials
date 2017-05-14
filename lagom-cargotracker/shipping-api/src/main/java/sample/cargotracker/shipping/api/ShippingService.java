package sample.cargotracker.shipping.api;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.restCall;

import akka.Done;
import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;

/**
 * The shipping service interface.
 * <p/>
 * This describes everything that Lagom needs to know about how to serve and consume the ShippingService.
 */
public interface ShippingService extends Service {

    /**
     * Example: curl -H "Content-Type: application/json" -X POST -d
     * '{
     *      "itinerary": {
     *          "id": "1",
     *          "cargoId": "1",
     *          "origin": "Gothom City",
     *          "destination": "Metropolis",
     *      }
     * }' http://localhost:9000/api/itinerary
     */
     ServiceCall<NotUsed, Itinerary, Done> createItinerary();

    /**
     * Adds a leg to an existing itinerary.
     *
     * The String here is the itinerary id.
     *
     * <pre>{@code curl -H "Content-Type: application/json" -X POST -d
     * '{
     *      "leg": {
     *          "id": "1",
     *          "cargoId": "cargoId",
     *          "location": "derp",
     *          "arrivalTime": arrivalTime,
     *          "departureTime": departureTime
     *      }
     * }' http://localhost:9000/api/itinerary/1/leg
     * }</pre>
     */
    ServiceCall<String, Leg, Done> addLeg();

    @Override
    default Descriptor descriptor() {
        // @formatter:off
        return named("shippingService").with(
            restCall(Method.POST, "/api/itinerary", createItinerary()).withAutoAcl(true),
            restCall(Method.POST, "/api/itinerary/:itinerary/leg", addLeg()).withAutoAcl(true)
        );
        // @formatter:on

        /*
       {
             "leg": {
                 "id": "1",
                 "cargoId": "cargoId",
                 "location": "derp",
                 "arrivalTime": "2002-05-30T09:30:10Z",
                 "departureTime": "2002-05-30T010:30:10Z"
             }
        }
         */
    }
}
