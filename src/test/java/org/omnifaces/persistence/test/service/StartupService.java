/*
 * Copyright 2020 OmniFaces
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.omnifaces.persistence.test.service;

import static java.lang.Math.abs;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.omnifaces.persistence.Provider;
import org.omnifaces.persistence.test.model.Address;
import org.omnifaces.persistence.test.model.Comment;
import org.omnifaces.persistence.test.model.Gender;
import org.omnifaces.persistence.test.model.Group;
import org.omnifaces.persistence.test.model.Person;
import org.omnifaces.persistence.test.model.Phone;
import org.omnifaces.persistence.test.model.Product;
import org.omnifaces.persistence.test.model.ProductStatus;
import org.omnifaces.persistence.test.model.Text;
import org.omnifaces.persistence.test.model.UserRole;

@Startup
@Singleton
public class StartupService {

	public static final int TOTAL_RECORDS = 200;
	public static final int ROWS_PER_PAGE = 10;

	@Inject
	private TextService textService;

	@Inject
	private CommentService commentService;

	@Inject
	private PersonService personService;

	@Inject
	private ProductService productService;

	@PostConstruct
	public void init() {
		createTestPersons();
		createTestTexts();
		createTestComments();
		createTestProducts();
	}

	private void createTestPersons() {
		Gender[] genders = Gender.values();
		Phone.Type[] phoneTypes = Phone.Type.values();
		List<Group> groups = Arrays.asList(Group.values());
		ThreadLocalRandom random = ThreadLocalRandom.current();

		for (int i = 0; i < TOTAL_RECORDS; i++) {
			Person person = new Person();
			person.setEmail("name" + i + "@example.com");
			person.setGender(genders[random.nextInt(genders.length)]);
			person.setDateOfBirth(LocalDate.ofEpochDay(random.nextLong(LocalDate.of(1900, 1, 1).toEpochDay(), LocalDate.of(2000, 1, 1).toEpochDay())));

			Address address = new Address();
			address.setStreet("Street" + i);
			address.setHouseNumber("" + i);
			address.setPostcode("Postcode" + i);
			address.setCity("City" + i);
			address.setCountry("Country" + i);
			person.setAddress(address);

			int totalPhones = random.nextInt(1, 6);
			for (int j = 0; j < totalPhones; j++) {
				Phone phone = new Phone();
				phone.setType(phoneTypes[random.nextInt(phoneTypes.length)]);
				phone.setNumber("0" + abs(random.nextInt()));
				phone.setOwner(person);
				person.getPhones().add(phone);
			}

			Collections.shuffle(groups, random);
			person.getGroups().addAll(groups.subList(0, random.nextInt(1, groups.size() + 1)));

			personService.persist(person);
		}
	}

	private void createTestTexts() {
		textService.persist(new Text());
		textService.persist(new Text());
	}

	private void createTestComments() {
		commentService.persist(new Comment());
		commentService.persist(new Comment());
	}

	private void createTestProducts() {
		if (productService.getProvider() != Provider.ECLIPSELINK) { // EclipseLink doesn't seem to support EnumMappingTableService's actions.
			Product product = new Product();
			product.setProductStatus(ProductStatus.IN_STOCK);
			product.addUserRole(UserRole.USER);
			productService.persist(product);

			product = new Product();
			product.setProductStatus(ProductStatus.DISCONTINUED);
			product.addUserRole(UserRole.EMPLOYEE);
			product.addUserRole(UserRole.MANAGER);
			productService.persist(product);
		}
	}

}
