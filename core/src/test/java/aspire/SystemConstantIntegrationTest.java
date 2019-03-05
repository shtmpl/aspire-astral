package aspire;

import aspire.astral.Application;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
public class SystemConstantIntegrationTest {

//    @Autowired
//    private SystemConstantRepository systemConstantRepository;
//
//    @Test
//    public void shouldAllowToSaveEntity() throws Exception {
//        SystemConstantEntity constant = new SystemConstantEntity();
//        constant.setName("tenderhub.url");
//        constant.setType("C");
//        constant.setValue("https://api.tenderhub.ru/api");
//        constant.setDescription("Базовый URL TenderHub");
//
//        SystemConstantEntity saved = systemConstantRepository.save(constant);
//
//        assertNotNull(saved.getId());
//    }
//
//    @Test
//    public void shouldAllowToFindSavedEntity() throws Exception {
//        SystemConstantEntity constant = new SystemConstantEntity();
//        constant.setName("tenderhub.url");
//        constant.setType("C");
//        constant.setValue("https://api.tenderhub.ru/api");
//        constant.setDescription("Базовый URL TenderHub");
//
//        SystemConstantEntity saved = systemConstantRepository.save(constant);
//
//        assertNotNull(saved.getId());
//
//        SystemConstantEntity found = systemConstantRepository.findByName("tenderhub.url").orElse(null);
//
//        assertNotNull(found);
//
//        assertThat(found.getName(), is(constant.getName()));
//        assertThat(found.getType(), is(constant.getType()));
//        assertThat(found.getValue(), is(constant.getValue()));
//        assertThat(found.getDescription(), is(constant.getDescription()));
//    }
//
//    @Test
//    public void shouldAllowToUpdateSavedEntity() throws Exception {
//        SystemConstantEntity constant = new SystemConstantEntity();
//        constant.setName("tenderhub.url");
//        constant.setType("C");
//        constant.setValue("https://api.tenderhub.ru/api");
//        constant.setDescription("Базовый URL TenderHub");
//
//        SystemConstantEntity saved = systemConstantRepository.save(constant);
//
//        assertNotNull(saved.getId());
//
//        saved.setValue("https://api.tenderhub.ru/other");
//
//        SystemConstantEntity updated = systemConstantRepository.save(saved);
//
//        assertThat(updated.getId(), is(saved.getId()));
//
//        SystemConstantEntity found = systemConstantRepository.findByName("tenderhub.url").orElse(null);
//
//        assertNotNull(found);
//
//        assertThat(found.getName(), is(constant.getName()));
//        assertThat(found.getType(), is(constant.getType()));
//        assertThat(found.getValue(), is(updated.getValue()));
//        assertThat(found.getDescription(), is(constant.getDescription()));
//    }
//
//    @Test
//    public void shouldAllowToDeleteSavedEntity() throws Exception {
//        SystemConstantEntity constant = new SystemConstantEntity();
//        constant.setName("tenderhub.url");
//        constant.setType("C");
//        constant.setValue("https://api.tenderhub.ru/api");
//        constant.setDescription("Базовый URL TenderHub");
//
//        SystemConstantEntity saved = systemConstantRepository.save(constant);
//
//        assertNotNull(saved.getId());
//
//        systemConstantRepository.delete(saved);
//
//        SystemConstantEntity found = systemConstantRepository.findByName("tenderhub.url").orElse(null);
//
//        assertNull(found);
//    }
}
