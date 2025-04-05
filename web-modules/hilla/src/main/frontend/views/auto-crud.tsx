import ContactModel from 'Frontend/generated/com/example/demo/ContactModel';
import { ContactService } from 'Frontend/generated/endpoints';
import { AutoCrud } from '@vaadin/hilla-react-crud';

export default function AutoCrudView() {
    return <AutoCrud service={ContactService} model={ContactModel} className="h-full" />;
}
