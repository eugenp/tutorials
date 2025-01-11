import { useEffect } from 'react';
import { useSignal } from '@vaadin/hilla-react-signals';
import { ContactService } from 'Frontend/generated/endpoints';
import { Grid, GridSortColumn } from '@vaadin/react-components';
import { NavLink } from 'react-router-dom';
import Contact from 'Frontend/generated/com/example/demo/Contact';
import { ViewConfig } from '@vaadin/hilla-file-router/types.js';

export const config: ViewConfig = {
    title: 'Contact List',
    menu: {
        order: 1
    }
};


export default function Contacts() {
    const contacts = useSignal<Contact[]>([]);

    async function fetchContacts() {
        contacts.value = await ContactService.findAll();
    }

    useEffect(() => {
        fetchContacts();
    }, []);

    return (
        <div className="p-m flex flex-col gap-m">
            <h2>Contacts</h2>
            <Grid items={contacts.value}>
                <GridSortColumn path="name">
                    {({ item }) => <NavLink to={`contacts/${item.id}/edit`}>{item.name}</NavLink>}
                </GridSortColumn>
                <GridSortColumn path="email" />
                <GridSortColumn path="phone" />
            </Grid>
        </div>
    );
}
