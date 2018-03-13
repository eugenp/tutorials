@RequestMapping(value="/{id}", method=RequestMethod.GET)
public String getSpittle(@PathVariable long id, Model model) {
  model.addAttribute(spitterService.getSpittleById(id));
  return "spittles/view";
}
