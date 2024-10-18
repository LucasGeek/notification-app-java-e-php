<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

/**
 * @OA\Schema(
 *     schema="NotificationTypeRequest",
 *     required={"nomeTipo"},
 *     @OA\Property(
 *         property="nomeTipo",
 *         type="string",
 *         description="Nome do tipo de notificação",
 *         maxLength=255,
 *         example="Novo Tipo de Notificação",
 *         pattern="^[A-Za-zÀ-ÿ ']{3,255}$",
 *         nullable=false
 *     )
 * )
 */
class NotificationTypeRequest extends FormRequest
{
    public function rules(): array
    {
        return [
            'nomeTipo' => 'required|string|max:255',
        ];
    }

    public function authorize(): bool
    {
        return true;
    }
}
