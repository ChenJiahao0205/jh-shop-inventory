package pers.jhshop.inventory;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@MapperScan(basePackages = "pers.jhshop.inventory.mapper")
@SpringBootApplication
public class JhShopInventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(JhShopInventoryServiceApplication.class, args);
    }

}
