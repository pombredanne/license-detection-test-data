<?php

/**
 * Generated data provider for int license unit test
 */
return function(\Faker\Generator $faker){
    return [
        'int license'   => [
            \Aedart\Model\Traits\Integers\LicenseTrait::class,
            \Aedart\Model\Contracts\Integers\LicenseAware::class,
            $faker->randomNumber($faker->randomDigitNotNull),
            $faker->randomNumber($faker->randomDigitNotNull),
        ]
    ];
};