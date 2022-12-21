# Coffee Corner

The application handles ordering of predefined products, applying discounts (1.: every 5th Beverage is free; 2.: if a beverage and a snack is ordered, one of the extra is free) and finally printing the receipt as a string representation of the order to the default System.out.

## Install

Via Maven

``` bash
$ mvn clean install
```

## Usage
### Example Code
``` java
final Beverage largeCoffeeWithExtraMilk = BeverageType.COFFEE_L.with(ExtraType.EXTRA_MILK);
final Beverage smallCoffeeWithSpecialRoast = BeverageType.COFFEE_S.with(ExtraType.SPECIAL_ROAST_COFFEE);
final Snack baconRoll = SnackType.BACON_ROLL.create();
final Beverage orangeJuice = BeverageType.ORANGE_JUICE.create();

final List<Product> products = Arrays.asList(largeCoffeeWithExtraMilk,
    smallCoffeeWithSpecialRoast, baconRoll, orangeJuice);

final CoffeeCorner coffeeCorner = new CoffeeCorner();
coffeeCorner.order(products);
coffeeCorner.pay();
```
### Example output

``` bash
----------------------------------------------------
------------- Charlene's Coffee Corner -------------
----------------------------------------------------
                  Large coffee	                3.50
                  + Extra milk	                0.30

                  Small coffee	                2.50
        + Special roast coffee	                0.00

                    Bacon Roll	                4.50
 Freshly squeezed orange juice	                3.95
----------------------------------------------------
----------------------------------------------------
                        Total:	           CHF 14.75
                         Date:	20.12.2022, 23:54:59
----------------------------------------------------
------------ Thank you for your order! -------------
----------------------------------------------------
```



## Testing

``` bash
$ mvn clean test
```

## Credits

- [Balázs Török][link-author]

## License

Copyright (c) 2022 Balázs Török. Please see [License File](LICENSE.md) for more information.

[link-author]: https://github.com/:torok_balazs
