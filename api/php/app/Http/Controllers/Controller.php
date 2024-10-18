<?php

namespace App\Http\Controllers;

use Illuminate\Foundation\Auth\Access\AuthorizesRequests;
use Illuminate\Foundation\Bus\DispatchesJobs;
use Illuminate\Foundation\Validation\ValidatesRequests;
use Illuminate\Routing\Controller as BaseController;

/**
 * @OA\Info(
 *     title="API de Notificações em PHP",
 *     version="1.0.0",
 *     description="Documentação da API de Notificações em Laravel",
 *     @OA\Contact(
 *         email="lucas.albuquerque@gmail.com",
 *         name="Lucas Albuquerque",
 *         url="https://www.linkedin.com/in/lucasgeek"
 *     )
 * ),
 * @OA\Server(
 *     url=L5_SWAGGER_CONST_HOST,
 *     description="API Host"
 * )
 * @OA\SecurityScheme(
 *      type="http",
 *      scheme="bearer",
 *      bearerFormat="JWT",
 *      securityScheme="bearerAuth"
 * )
 */
class Controller extends BaseController
{
    use AuthorizesRequests, DispatchesJobs, ValidatesRequests;
}
