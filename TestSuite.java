package com.mavenIT.Automation;

import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;
public class TestSuite extends Hooks {

    @Test
    public void basketTest(){

        WebDriverWait wait = new WebDriverWait(driver,10);

        doSearch("watch");

        List<WebElement> productItem = driver.findElements(By.cssSelector("a[data-test='component-product-card-title']"));

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("a[data-test='component-product-card-title']")));

        if (productItem.size() == 0) {
            fail("There is  not a such product related to your search");
        }

        Random rand = new Random();

        int randInt = rand.nextInt(productItem.size()-1);

        WebElement selectedProduct = productItem.get(randInt);

        String expected = selectedProduct.getText();

        wait.until(ExpectedConditions.elementToBeClickable(selectedProduct));

        selectedProduct.click();


        addToTrolley();

        gotoTrolley();

        String actual = getProductNameInBasket();

        assertThat(actual, is(equalTo(expected)));
    }

    @Test

    public void addTwoDifferentItemToBasket(){

        WebDriverWait wait =new WebDriverWait(driver,20);

        doSearch("puma");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[data-test='component-product-card-title']")));
        List<WebElement> productItem = driver.findElements(By.cssSelector("a[data-test='component-product-card-title']"));

        if(productItem.size() == 0){
            fail();
        }
        Random random = new Random();

        WebElement selected1 = productItem.get(random.nextInt(productItem.size()-1));

        String expected1 = selected1.getText();

        selected1.click();

        addToTrolley();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button[data-test = 'component-att-modal-button-close']")));

        driver.findElement(By.cssSelector("button[data-test = 'component-att-modal-button-close'] ")).click();

        driver.navigate().back();

        List<WebElement> productItems = driver.findElements(By.cssSelector("a[data-test='component-product-card-title']"));

        WebElement selected2 = productItems.get(random.nextInt(productItems.size()-1));

        String expected2 = selected2.getText();

        selected2.click();

        addToTrolley();

        gotoTrolley();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".ProductCard__content__9U9b1.xsHidden")));

        List<WebElement>basketProduct = driver.findElements(By.cssSelector(".ProductCard__content__9U9b1.xsHidden"));

        int noOfItem = basketProduct.size();

            System.out.println(noOfItem);

            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".ProductCard__content__9U9b1.xsHidden")));

        List<WebElement>basketProduct = driver.findElements(By.cssSelector(".ProductCard__content__9U9b1.xsHidden"));

        int noOfItem = basketProduct.size();

            System.out.println(noOfItem);

            List< String > values = new ArrayList<>();

            for(WebElement product: basketProduct) {

                values.add(product.getText());
                
                assertThat(values, containsInAnyOrder(expected1,expected2));


    }


    }

    @Test
        public void addOneMoreBasket (){

            WebDriverWait wait = new WebDriverWait(driver,10);

            doSearch("table lamp");


            List<WebElement> tableLamps = driver.findElements(By.cssSelector("a[data-test='component-product-card-title'] "));

            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("a[data-test='component-product-card-title']")));

            if (tableLamps.size() == 0) {
                fail("No any item related to your search");

            }
            Random random = new Random();

            int ranInt = random.nextInt(tableLamps.size() - 1);

            WebElement selected = tableLamps.get(ranInt);

            String expectedName = selected.getText();

            selected.click();

            addToTrolley();

            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button[data-test='component-att-button-continue")));

            driver.findElement(By.cssSelector("  button[data-test='component-att-button-continue']")).click();

            addToTrolley();

            gotoTrolley();

            String actual = getProductNameInBasket();

            assertThat(actual, is(equalToIgnoringCase(expectedName)));

            String amount = productQuantity();

            assertThat(amount, is("2"));


        }


        public void doSearch (String searchProduct){
            driver.findElement(By.id("searchTerm")).
                    sendKeys(searchProduct);
            driver.findElement(By.id("searchTerm")).
                    sendKeys(Keys.ENTER);

        }

        public void addToTrolley () {
            WebDriverWait wait = new WebDriverWait(driver,25);

            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button[data-test='component-att-button")));

            WebElement addToTrolley =  driver.findElement(By.cssSelector("button[data-test='component-att-button']"));

            wait.until(ExpectedConditions.elementToBeClickable(addToTrolley));

            addToTrolley.click();
        }

        public void gotoTrolley () {

            WebDriverWait wait = new WebDriverWait(driver,20);

            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".xs-row a[data-test='component-att-button-basket']")));

            WebElement gotoTrolley = driver.findElement(By.cssSelector(".xs-row a[data-test='component-att-button-basket']"));

            wait.until(ExpectedConditions.elementToBeClickable(gotoTrolley));

            gotoTrolley.click();

        }

        public String getProductNameInBasket () {

            WebDriverWait wait = new WebDriverWait(driver,20);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".ProductCard__content__9U9b1.xsHidden.lgFlex .ProductCard__titleLink__1PgaZ")));
            WebElement productName = driver.findElement(By.cssSelector(".ProductCard__content__9U9b1.xsHidden.lgFlex .ProductCard__titleLink__1PgaZ"));
            return productName.getText();
        }

        public String productQuantity () {

            Select select = new Select(driver.findElement(By.cssSelector("select[data-e2e='product-quantity']")));
            String option = select.getFirstSelectedOption().getText();
            return option;
        }


    }
