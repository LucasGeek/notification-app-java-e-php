<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

/**
 * @OA\Schema(
 *     schema="UserLoginRequest",
 *     required={"email", "password"},
 *     @OA\Property(property="email", type="string", format="email", description="Email do usuário"),
 *     @OA\Property(property="password", type="string", format="password", description="password do usuário")
 * )
 */
class UserLoginRequest extends FormRequest
{
    public function rules(): array
    {
        return [
            'email' => 'required|string|email|max:255',
            'password' => 'required|string',
        ];
    }

    public function authorize(): bool
    {
        return true;
    }
}
