<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

/**
 * @OA\Schema(
 *     schema="UserRegisterRequest",
 *     required={"nome", "sobrenome", "email", "password"},
 *     @OA\Property(property="nome", type="string", description="Nome do usuário"),
 *     @OA\Property(property="sobrenome", type="string", description="Sobrenome do usuário"),
 *     @OA\Property(property="email", type="string", format="email", description="Email do usuário"),
 *     @OA\Property(property="password", type="string", format="password", description="password do usuário (mínimo de 8 caracteres)")
 * )
 */
class UserRegisterRequest extends FormRequest
{
    public function rules(): array
    {
        return [
            'nome' => 'required|string|max:255',
            'sobrenome' => 'required|string|max:255',
            'email' => 'required|string|email|max:255|unique:users,email',
            'password' => 'required|string|min:8',
        ];
    }

    public function authorize(): bool
    {
        return true;
    }
}
