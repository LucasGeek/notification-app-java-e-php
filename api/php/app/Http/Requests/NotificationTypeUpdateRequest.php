<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

/**
 * @OA\Schema(
 *     schema="NotificationTypeUpdateRequest",
 *     required={"novoNomeTipo"},
 *     @OA\Property(
 *         property="novoNomeTipo",
 *         type="string",
 *         description="Novo nome do tipo de notificação",
 *         maxLength=255,
 *         example="Atualização do Tipo de Notificação",
 *         pattern="^[A-Za-zÀ-ÿ ']{3,255}$",
 *         nullable=false
 *     )
 * )
 */
class NotificationTypeUpdateRequest extends FormRequest
{
    public function rules(): array
    {
        return [
            'novoNomeTipo' => 'required|string|max:255'
        ];
    }

    public function authorize(): bool
    {
        return true;
    }
}
