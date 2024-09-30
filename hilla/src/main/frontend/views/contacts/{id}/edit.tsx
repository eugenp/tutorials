import {Button, TextField} from "@vaadin/react-components";
import {useEffect} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {useForm} from "@vaadin/hilla-react-form";
import {ContactService} from "Frontend/generated/endpoints";
import ContactModel from "Frontend/generated/com/example/demo/ContactModel";

export default function ContactEditor() {
  const {id} = useParams();
  const navigate = useNavigate();

  const {field, model, submit, read} = useForm(ContactModel, {
    onSubmit: async contact => {
      await ContactService.save(contact);
      navigate('/');
    }
  })

  async function loadUser(id: number) {
    read(await ContactService.findById(id))
  }

  useEffect(() => {
    if (id) {
      loadUser(parseInt(id))
    }
  }, [id]);


  return (
    <div className="flex flex-col items-start gap-m">
      <TextField label="Name" {...field(model.name)}/>
      <TextField label="Email" {...field(model.email)}/>
      <TextField label="Phone" {...field(model.phone)}/>
      <div className="flex gap-s">
        <Button onClick={submit} theme="primary">Save</Button>
        <Button onClick={() => navigate('/')} theme="tertiary">Cancel</Button>
      </div>
    </div>
  );
}
