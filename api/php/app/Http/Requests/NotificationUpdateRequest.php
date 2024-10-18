<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

/**
 * @OA\Schema(
 *     schema="NotificationUpdateRequest",
 *     required={"titulo", "descricao", "corpo"},
 *     @OA\Property(
 *         property="titulo",
 *         type="string",
 *         description="Título da notificação",
 *         maxLength=255,
 *         example="Atualização da notificação"
 *     ),
 *     @OA\Property(
 *         property="descricao",
 *         type="string",
 *         description="Descrição da notificação",
 *         maxLength=255,
 *         example="A notificação foi atualizada."
 *     ),
 *     @OA\Property(
 *         property="corpo",
 *         type="string",
 *         description="Corpo completo da notificação",
 *         example="A notificação foi alterada para refletir novas informações."
 *     ),
 *     @OA\Property(
 *         property="imagemDestaque",
 *         type="string",
 *         format="url",
 *         description="URL opcional da imagem de destaque",
 *         nullable=true,
 *         example="https://exemplo.com/atualizacao-imagem.png"
 *     )
 * )
 */
class NotificationUpdateRequest extends FormRequest
{
    public function rules(): array
    {
        return [
            'titulo' => 'required|string|max:255',
            'descricao' => 'required|string|max:255',
            'corpo' => 'required|string',
            'imagemDestaque' => 'nullable|url',
        ];
    }

    public function authorize(): bool
    {
        return true;
    }
}
