package mocks

import spock.lang.Specification

class ItemServiceUnitTest extends Specification {

    ItemProvider itemProvider
    ItemService itemService
    EventPublisher eventPublisher

    def setup() {
        itemProvider = Stub(ItemProvider)
        eventPublisher = Mock(EventPublisher)
        itemService = new ItemService(itemProvider, eventPublisher)
    }

    def 'should return items sorted by name'() {
        given:
        def ids = ['offer-id', 'offer-id-2']
        itemProvider.getItems(ids) >> [new Item('offer-id-2', 'Zname'), new Item('offer-id', 'Aname')]

        when:
        List<Item> items = itemService.getAllItemsSortedByName(ids)

        then:
        items.collect { it.name } == ['Aname', 'Zname']
    }

    def 'arguments constraints'() {
        itemProvider.getItems(['offer-id'])
        itemProvider.getItems(_) >> []
        itemProvider.getItems(*_) >> []
        itemProvider.getItems(!null) >> []
        itemProvider.getItems({ it.size > 0 }) >> []
    }

    def 'should return different items on subsequent call'() {
        given:
        itemProvider.getItems(_) >>> [
                [],
                [new Item('1', 'name')],
                [new Item('2', 'name')]
        ]

        when: 'method is called for the first time'
        List<Item> items = itemService.getAllItemsSortedByName(['not-important'])

        then: 'empty list is returned'
        items == []

        when: 'method is called for the second time'
        items = itemService.getAllItemsSortedByName(['not-important'])

        then: 'item with id=1 is returned'
        items == [new Item('1', 'name')]

        when: 'method is called for the thirdtime'
        items = itemService.getAllItemsSortedByName(['not-important'])

        then: 'item with id=2 is returned'
        items == [new Item('2', 'name')]
    }

    def 'should throw ExternalItemProviderException when ItemProvider fails'() {
        given:
        itemProvider.getItems(_) >> { new RuntimeException()}

        when:
        itemService.getAllItemsSortedByName([])

        then:
        thrown(ExternalItemProviderException)
    }

    def 'chaining response'() {
        itemProvider.getItems(_) >>> { new RuntimeException() } >> new SocketTimeoutException() >> [new Item('id', 'name')]
    }

    def 'should return different items for different ids lists'() {
        given:
        def firstIds = ['first']
        def secondIds = ['second']
        itemProvider.getItems(firstIds) >> [new Item('first', 'Zname')]
        itemProvider.getItems(secondIds) >> [new Item('second', 'Aname')]

        when:
        def firstItems = itemService.getAllItemsSortedByName(firstIds)
        def secondItems = itemService.getAllItemsSortedByName(secondIds)

        then:
        firstItems.first().name == 'Zname'
        secondItems.first().name == 'Aname'
    }

    def 'should publish events about new non-empty saved offers'() {
        given:
        def offerIds = ['', 'a', 'b']

        when:
        itemService.saveItems(offerIds)

        then:
        2 * eventPublisher.publish({ it != null && !it.isEmpty()})
    }

    def 'should return items'() {
        given:
        itemProvider = Mock(ItemProvider)
        itemProvider.getItems(['item-id']) >> [new Item('item-id', 'name')]
        itemService = new ItemService(itemProvider, eventPublisher)

        when:
        def items = itemService.getAllItemsSortedByName(['item-id'])

        then:
        items == [new Item('item-id', 'name')]
    }

    def 'should spy on EventPublisher method call'() {
        given:
        LoggingEventPublisher eventPublisher = Spy(LoggingEventPublisher)
        itemService = new ItemService(itemProvider, eventPublisher)

        when:
        itemService.saveItems(['item-id'])

        then:
        1 * eventPublisher.publish('item-id')
    }

}
