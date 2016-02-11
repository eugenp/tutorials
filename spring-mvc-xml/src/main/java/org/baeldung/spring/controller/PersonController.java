package org.baeldung.spring.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.baeldung.spring.form.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PersonController {

	@RequestMapping(value = "/person", method = RequestMethod.GET)
	public ModelAndView initForm(final Model model) {

		final List<String> favouriteLanguage = new ArrayList<String>();
		favouriteLanguage.add("Java");
		favouriteLanguage.add("C++");
		favouriteLanguage.add("Perl");
		model.addAttribute("favouriteLanguage", favouriteLanguage);

		final List<String> job = new ArrayList<String>();
		job.add("Full time");
		job.add("Part time");
		model.addAttribute("job", job);

		final Map<String, String> country = new LinkedHashMap<String, String>();
		country.put("US", "United Stated");
		country.put("IT", "Italy");
		country.put("UK", "United Kingdom");
		country.put("FR", "Grance");
		model.addAttribute("country", country);

		final List<String> fruit = new ArrayList<String>();
		fruit.add("Banana");
		fruit.add("Mango");
		fruit.add("Apple");
		model.addAttribute("fruit", fruit);

		final List<String> books = new ArrayList<String>();
		books.add("The Great Gatsby");
		books.add("Nineteen Eighty-Four");
		books.add("The Lord of the Rings");
		model.addAttribute("books", books);

		return new ModelAndView("personForm", "person", new Person());
	}

	@RequestMapping(value = "/addPerson", method = RequestMethod.POST)
	public String submit(@Valid @ModelAttribute("person") final Person person, final BindingResult result,
			final ModelMap model) {

		if (result.hasErrors()) {
			return "error";
		}

		model.addAttribute("person", person);

		return "personResume";
	}
	//
	// protected Map<String, List<String>> referenceData(final
	// HttpServletRequest request) throws Exception {
	//
	// final Map<String, List<String>> referenceData = new HashMap<>();
	//
	// final List<String> favouriteLanguageList = new ArrayList<String>();
	// favouriteLanguageList.add("Java");
	// favouriteLanguageList.add("C++");
	// favouriteLanguageList.add("Perl");
	// referenceData.put("favouriteLanguageList", favouriteLanguageList);
	//
	// return referenceData;
	//
	// }
}
