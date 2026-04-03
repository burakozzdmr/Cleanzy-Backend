package com.burakozdemir.cleanzy.config;

import com.burakozdemir.cleanzy.auth.entity.Role;
import com.burakozdemir.cleanzy.auth.entity.User;
import com.burakozdemir.cleanzy.auth.repository.AuthRepository;
import com.burakozdemir.cleanzy.cleaner.entity.Cleaner;
import com.burakozdemir.cleanzy.cleaner.repository.CleanerRepository;
import com.burakozdemir.cleanzy.common.util.ServiceType;
import com.burakozdemir.cleanzy.customer.entity.Customer;
import com.burakozdemir.cleanzy.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final AuthRepository authRepository;
    private final CustomerRepository customerRepository;
    private final CleanerRepository cleanerRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String DEFAULT_PASSWORD = "Test1234!";

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        String encodedPassword = passwordEncoder.encode(DEFAULT_PASSWORD);

        if (customerRepository.count() == 0) {
            log.info("Inserting 25 dummy customers...");
            seedCustomers(encodedPassword);
            log.info("Customer seed complete.");
        } else {
            log.info("Customers already exist, skipping customer seed.");
        }

        if (cleanerRepository.count() == 0) {
            log.info("Inserting 25 dummy cleaners...");
            seedCleaners(encodedPassword);
            log.info("Cleaner seed complete.");
        } else {
            log.info("Cleaners already exist, skipping cleaner seed.");
        }
    }

    // ─── Customers ────────────────────────────────────────────────────────────

    private void seedCustomers(String encodedPassword) {
        List<Object[]> customerData = List.of(
                new Object[]{"Ahmet Yılmaz",      "ahmet.yilmaz@cleanzy.com",      "Kadıköy, İstanbul",       4.8, 32},
                new Object[]{"Mehmet Kaya",        "mehmet.kaya@cleanzy.com",        "Beşiktaş, İstanbul",      4.5, 18},
                new Object[]{"Ayşe Demir",         "ayse.demir@cleanzy.com",         "Şişli, İstanbul",         4.9, 47},
                new Object[]{"Fatma Çelik",        "fatma.celik@cleanzy.com",        "Üsküdar, İstanbul",       4.3, 11},
                new Object[]{"Ali Şahin",          "ali.sahin@cleanzy.com",          "Bağcılar, İstanbul",      4.6, 25},
                new Object[]{"Zeynep Aydın",       "zeynep.aydin@cleanzy.com",       "Maltepe, İstanbul",       4.7, 39},
                new Object[]{"Mustafa Arslan",     "mustafa.arslan@cleanzy.com",     "Ataşehir, İstanbul",      4.2, 8},
                new Object[]{"Emine Doğan",        "emine.dogan@cleanzy.com",        "Bakırköy, İstanbul",      4.9, 53},
                new Object[]{"Hüseyin Kılıç",      "huseyin.kilic@cleanzy.com",      "Pendik, İstanbul",        4.4, 15},
                new Object[]{"Hatice Yıldız",      "hatice.yildiz@cleanzy.com",      "Kartal, İstanbul",        4.6, 29},
                new Object[]{"İbrahim Özdemir",    "ibrahim.ozdemir@cleanzy.com",    "Sarıyer, İstanbul",       4.8, 41},
                new Object[]{"Elif Çetin",         "elif.cetin@cleanzy.com",         "Eyüpsultan, İstanbul",    4.5, 22},
                new Object[]{"Hasan Erdoğan",      "hasan.erdogan@cleanzy.com",      "Tuzla, İstanbul",         4.1, 7},
                new Object[]{"Merve Güneş",        "merve.gunes@cleanzy.com",        "Esenyurt, İstanbul",      4.7, 34},
                new Object[]{"Ömer Polat",         "omer.polat@cleanzy.com",         "Sultangazi, İstanbul",    4.3, 13},
                new Object[]{"Seda Aktaş",         "seda.aktas@cleanzy.com",         "Zeytinburnu, İstanbul",   4.9, 61},
                new Object[]{"Murat Yüksel",       "murat.yuksel@cleanzy.com",       "Güngören, İstanbul",      4.0, 5},
                new Object[]{"Büşra Koç",          "busra.koc@cleanzy.com",          "Esenler, İstanbul",       4.6, 27},
                new Object[]{"Serkan Avcı",        "serkan.avci@cleanzy.com",        "Bayrampaşa, İstanbul",    4.4, 19},
                new Object[]{"Gülşen Bulut",       "gulsen.bulut@cleanzy.com",       "Silivri, İstanbul",       4.8, 44},
                new Object[]{"Tolga Tunç",         "tolga.tunc@cleanzy.com",         "Arnavutköy, İstanbul",    4.2, 9},
                new Object[]{"Derya Şimşek",       "derya.simsek@cleanzy.com",       "Çekmeköy, İstanbul",      4.7, 36},
                new Object[]{"Kemal Özcan",        "kemal.ozcan@cleanzy.com",        "Sancaktepe, İstanbul",    4.5, 21},
                new Object[]{"Neslihan Kurt",      "neslihan.kurt@cleanzy.com",      "Sultanbeyli, İstanbul",   4.9, 58},
                new Object[]{"Emre Karaca",        "emre.karaca@cleanzy.com",        "Başakşehir, İstanbul",    4.3, 16}
        );

        for (Object[] data : customerData) {
            String  fullName     = (String)  data[0];
            String  email        = (String)  data[1];
            String  location     = (String)  data[2];
            Double  rating       = (Double)  data[3];
            Integer totalReviews = (Integer) data[4];

            if (authRepository.existsByEmail(email)) continue;

            User savedUser = authRepository.save(User.builder()
                    .fullName(fullName)
                    .email(email)
                    .password(encodedPassword)
                    .role(Role.CUSTOMER)
                    .build());

            Customer customer = new Customer();
            customer.setUser(savedUser);
            customer.setCurrentLocation(location);
            customer.setRating(rating);
            customer.setTotalReviews(totalReviews);
            customer.setVerified(true);
            customer.setSavedAddresses(Map.of("Ev", location));
            customerRepository.save(customer);
        }
    }

    // ─── Cleaners ─────────────────────────────────────────────────────────────

    private void seedCleaners(String encodedPassword) {
        List<Object[]> cleanerData = List.of(
                new Object[]{"Canan Yıldırım",    "canan.yildirim@cleanzy.com",    "Kadıköy, İstanbul",     4.9, 87,  "150.00", Set.of("Kadıköy", "Üsküdar", "Ataşehir"),     Set.of(ServiceType.HOME_CLEANING, ServiceType.DEEP_CLEANING),        "Evlere ve ofislere 5 yıldır profesyonel temizlik hizmeti sunuyorum."},
                new Object[]{"Bülent Karakas",    "bulent.karakas@cleanzy.com",    "Beşiktaş, İstanbul",    4.7, 64,  "175.00", Set.of("Beşiktaş", "Şişli", "Sarıyer"),        Set.of(ServiceType.OFFICE_CLEANING, ServiceType.WINDOW_CLEANING),      "Kurumsal müşterilere özel ofis temizliği konusunda uzmanım."},
                new Object[]{"Sevgi Arslan",      "sevgi.arslan@cleanzy.com",      "Şişli, İstanbul",       4.8, 102, "140.00", Set.of("Şişli", "Beyoğlu", "Kağıthane"),       Set.of(ServiceType.HOME_CLEANING, ServiceType.WINDOW_CLEANING),        "Titiz ve güvenilir temizlik hizmeti. Müşteri memnuniyeti önceliğim."},
                new Object[]{"Kadir Özturk",      "kadir.ozturk@cleanzy.com",      "Üsküdar, İstanbul",     4.5, 43,  "130.00", Set.of("Üsküdar", "Kadıköy", "Maltepe"),       Set.of(ServiceType.DEEP_CLEANING, ServiceType.MOVING_CLEANUP),         "Taşınma sonrası ve derin temizlik hizmetlerinde 3 yıllık deneyim."},
                new Object[]{"Reyhan Demir",      "reyhan.demir@cleanzy.com",      "Maltepe, İstanbul",     4.6, 58,  "145.00", Set.of("Maltepe", "Kartal", "Pendik"),         Set.of(ServiceType.HOME_CLEANING, ServiceType.DEEP_CLEANING),          "Hijyen ve temizliğe verdiğim önemle her eve fark yaratıyorum."},
                new Object[]{"Tarık Güler",       "tarik.guler@cleanzy.com",       "Ataşehir, İstanbul",    4.4, 31,  "120.00", Set.of("Ataşehir", "Ümraniye", "Sancaktepe"), Set.of(ServiceType.OFFICE_CLEANING, ServiceType.HOME_CLEANING),         "Hem ev hem ofis temizliğinde esnek çalışma saatleriyle hizmetinizdeyim."},
                new Object[]{"Pınar Kocaman",     "pinar.kocaman@cleanzy.com",     "Bakırköy, İstanbul",    4.9, 119, "160.00", Set.of("Bakırköy", "Bağcılar", "Güngören"),   Set.of(ServiceType.HOME_CLEANING, ServiceType.DEEP_CLEANING, ServiceType.MOVING_CLEANUP), "10 yılı aşkın deneyimimle en zorlu temizlikleri bile üstleniyorum."},
                new Object[]{"Ufuk Çelik",        "ufuk.celik@cleanzy.com",        "Pendik, İstanbul",      4.3, 27,  "115.00", Set.of("Pendik", "Tuzla", "Kartal"),           Set.of(ServiceType.WINDOW_CLEANING, ServiceType.HOME_CLEANING),        "Cam ve pencere temizliğinde uzmanlaşmış, sigortalı hizmet."},
                new Object[]{"Sibel Aydın",       "sibel.aydin@cleanzy.com",       "Kartal, İstanbul",      4.7, 75,  "155.00", Set.of("Kartal", "Maltepe", "Pendik"),         Set.of(ServiceType.HOME_CLEANING, ServiceType.DEEP_CLEANING),          "Ailecek güvenebileceğiniz, referanslı temizlik uzmanı."},
                new Object[]{"Orkun Şahin",       "orkun.sahin@cleanzy.com",       "Sarıyer, İstanbul",     4.6, 49,  "165.00", Set.of("Sarıyer", "Beşiktaş", "Eyüpsultan"),  Set.of(ServiceType.OFFICE_CLEANING, ServiceType.DEEP_CLEANING),        "Büyük ofis projeleri ve derin temizlikte tam donanımlı ekip."},
                new Object[]{"Gamze Polat",       "gamze.polat@cleanzy.com",       "Eyüpsultan, İstanbul",  4.8, 91,  "135.00", Set.of("Eyüpsultan", "Sultangazi", "Bağcılar"),Set.of(ServiceType.HOME_CLEANING, ServiceType.WINDOW_CLEANING),        "Temizliği bir sanat olarak gören, detaycı bir profesyonel."},
                new Object[]{"Levent Koç",        "levent.koc@cleanzy.com",        "Tuzla, İstanbul",       4.2, 18,  "110.00", Set.of("Tuzla", "Pendik", "Sultanbeyli"),      Set.of(ServiceType.MOVING_CLEANUP, ServiceType.HOME_CLEANING),         "Taşınma temizliği ve genel ev bakımında ekonomik çözümler sunuyorum."},
                new Object[]{"Nihal Erdoğan",     "nihal.erdogan@cleanzy.com",     "Esenyurt, İstanbul",    4.7, 66,  "125.00", Set.of("Esenyurt", "Bağcılar", "Güngören"),   Set.of(ServiceType.HOME_CLEANING, ServiceType.DEEP_CLEANING),          "Hızlı ve güvenilir hizmet anlayışıyla Avrupa yakasında aktif olarak çalışıyorum."},
                new Object[]{"Volkan Yılmaz",     "volkan.yilmaz@cleanzy.com",     "Sultangazi, İstanbul",  4.5, 38,  "120.00", Set.of("Sultangazi", "Eyüpsultan", "Arnavutköy"), Set.of(ServiceType.OFFICE_CLEANING, ServiceType.WINDOW_CLEANING),   "Temiz iş yeri, verimli çalışan. Ofis temizliğinde güvenilir partner."},
                new Object[]{"Asuman Bulut",      "asuman.bulut@cleanzy.com",      "Zeytinburnu, İstanbul", 4.8, 83,  "145.00", Set.of("Zeytinburnu", "Bakırköy", "Güngören"), Set.of(ServiceType.HOME_CLEANING, ServiceType.DEEP_CLEANING),          "Eko-dostu ürünlerle çevre bilincinde temizlik yapıyorum."},
                new Object[]{"Cenk Aktaş",        "cenk.aktas@cleanzy.com",        "Güngören, İstanbul",    4.4, 29,  "115.00", Set.of("Güngören", "Bağcılar", "Esenler"),    Set.of(ServiceType.HOME_CLEANING, ServiceType.MOVING_CLEANUP),         "Uygun fiyat, kaliteli temizlik. Yeni müşterilere ilk hizmet indirimi."},
                new Object[]{"Figen Kaya",        "figen.kaya@cleanzy.com",        "Esenler, İstanbul",     4.6, 54,  "130.00", Set.of("Esenler", "Bağcılar", "Sultangazi"),  Set.of(ServiceType.HOME_CLEANING, ServiceType.WINDOW_CLEANING, ServiceType.DEEP_CLEANING), "Ayrıntılara gösterdiğim özenle evinizi pırıl pırıl bırakıyorum."},
                new Object[]{"Hakan Tunç",        "hakan.tunc@cleanzy.com",        "Bayrampaşa, İstanbul",  4.3, 22,  "120.00", Set.of("Bayrampaşa", "Gaziosmanpaşa", "Eyüpsultan"), Set.of(ServiceType.OFFICE_CLEANING, ServiceType.HOME_CLEANING),  "Saat esnekliği ve güler yüzlü hizmetle yanınızdayım."},
                new Object[]{"Yeliz Özcan",       "yeliz.ozcan@cleanzy.com",       "Silivri, İstanbul",     4.7, 71,  "135.00", Set.of("Silivri", "Büyükçekmece", "Esenyurt"), Set.of(ServiceType.HOME_CLEANING, ServiceType.DEEP_CLEANING),         "Deniz kenarı ve villa temizliğinde özel deneyimim var."},
                new Object[]{"Barış Karaca",      "baris.karaca@cleanzy.com",      "Arnavutköy, İstanbul",  4.5, 40,  "125.00", Set.of("Arnavutköy", "Başakşehir", "Sultangazi"), Set.of(ServiceType.HOME_CLEANING, ServiceType.MOVING_CLEANUP),     "Yeni taşınanlar için kapsamlı paket hizmetler sunuyorum."},
                new Object[]{"Selma Güneş",       "selma.gunes@cleanzy.com",       "Çekmeköy, İstanbul",    4.8, 96,  "155.00", Set.of("Çekmeköy", "Sancaktepe", "Ümraniye"),  Set.of(ServiceType.HOME_CLEANING, ServiceType.DEEP_CLEANING, ServiceType.OFFICE_CLEANING), "Anadolu yakasının güvenilir temizlik uzmanı, 8 yıllık tecrübe."},
                new Object[]{"Uğur Şimşek",       "ugur.simsek@cleanzy.com",       "Sancaktepe, İstanbul",  4.4, 33,  "115.00", Set.of("Sancaktepe", "Ümraniye", "Çekmeköy"),  Set.of(ServiceType.WINDOW_CLEANING, ServiceType.OFFICE_CLEANING),      "Yüksek katlı binalarda cam temizliği konusunda ekipli uzman."},
                new Object[]{"Mehtap Kurt",       "mehtap.kurt@cleanzy.com",       "Sultanbeyli, İstanbul", 4.6, 62,  "130.00", Set.of("Sultanbeyli", "Pendik", "Kartal"),     Set.of(ServiceType.HOME_CLEANING, ServiceType.DEEP_CLEANING),          "Her bütçeye uygun esnek paketlerle kaliteli temizlik hizmeti."},
                new Object[]{"Alper Doğan",       "alper.dogan@cleanzy.com",       "Başakşehir, İstanbul",  4.7, 78,  "140.00", Set.of("Başakşehir", "Esenyurt", "Arnavutköy"), Set.of(ServiceType.OFFICE_CLEANING, ServiceType.DEEP_CLEANING),       "Modern ofis komplekslerinde profesyonel temizlik çözümleri."},
                new Object[]{"Gönül Çetin",       "gonul.cetin@cleanzy.com",       "Ümraniye, İstanbul",    4.9, 108, "170.00", Set.of("Ümraniye", "Ataşehir", "Çekmeköy"),   Set.of(ServiceType.HOME_CLEANING, ServiceType.DEEP_CLEANING, ServiceType.MOVING_CLEANUP), "Mükemmeliyetçi yaklaşımım ve doğal temizlik ürünlerimle fark yaratıyorum."}
        );

        Map<DayOfWeek, String> weekdaySchedule = Map.of(
                DayOfWeek.MONDAY,    "08:00-18:00",
                DayOfWeek.TUESDAY,   "08:00-18:00",
                DayOfWeek.WEDNESDAY, "08:00-18:00",
                DayOfWeek.THURSDAY,  "08:00-18:00",
                DayOfWeek.FRIDAY,    "08:00-18:00"
        );

        Map<DayOfWeek, String> fullWeekSchedule = Map.of(
                DayOfWeek.MONDAY,    "09:00-17:00",
                DayOfWeek.TUESDAY,   "09:00-17:00",
                DayOfWeek.WEDNESDAY, "09:00-17:00",
                DayOfWeek.THURSDAY,  "09:00-17:00",
                DayOfWeek.FRIDAY,    "09:00-17:00",
                DayOfWeek.SATURDAY,  "10:00-16:00"
        );

        for (int i = 0; i < cleanerData.size(); i++) {
            Object[] data = cleanerData.get(i);
            String      fullName     = (String)           data[0];
            String      email        = (String)           data[1];
            String      location     = (String)           data[2];
            Double      rating       = (Double)           data[3];
            Integer     totalReviews = (Integer)          data[4];
            BigDecimal  hourlyRate   = new BigDecimal((String) data[5]);
            @SuppressWarnings("unchecked")
            Set<String> serviceArea  = (Set<String>)      data[6];
            @SuppressWarnings("unchecked")
            Set<ServiceType> services = (Set<ServiceType>) data[7];
            String      biography    = (String)           data[8];

            if (authRepository.existsByEmail(email)) continue;

            User savedUser = authRepository.save(User.builder()
                    .fullName(fullName)
                    .email(email)
                    .password(encodedPassword)
                    .role(Role.CLEANER)
                    .build());

            Cleaner cleaner = new Cleaner();
            cleaner.setUser(savedUser);
            cleaner.setCurrentLocation(location);
            cleaner.setRating(rating);
            cleaner.setTotalReviews(totalReviews);
            cleaner.setHourlyRate(hourlyRate);
            cleaner.setServiceArea(serviceArea);
            cleaner.setServices(services);
            cleaner.setBiography(biography);
            cleaner.setSchedule(i % 2 == 0 ? weekdaySchedule : fullWeekSchedule);
            cleaner.setVerified(true);
            cleaner.setAvailable(true);
            cleanerRepository.save(cleaner);
        }
    }
}
