<?php

/**
 * Generated data provider for string license unit test
 */
return function(\Faker\Generator $faker){
    return [
        'string license'   => [
            \Aedart\Model\Traits\Strings\LicenseTrait::class,
            \Aedart\Model\Contracts\Strings\LicenseAware::class,
            $faker->word,
            $faker->word,
        ]
    ];
};