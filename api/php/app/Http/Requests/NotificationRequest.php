<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

/**
 * @OA\Schema(
 *     schema="NotificationRequest",
 *     required={"typeId", "titulo", "descricao", "corpo"},
 *     @OA\Property(
 *         property="typeId",
 *         type="integer",
 *         description="ID do tipo de notificação"
 *     ),
 *     @OA\Property(
 *         property="titulo",
 *         type="string",
 *         description="Título da notificação",
 *         maxLength=255,
 *         example="Nova atualização disponível"
 *     ),
 *     @OA\Property(
 *         property="descricao",
 *         type="string",
 *         description="Descrição da notificação",
 *         maxLength=255,
 *         example="Uma nova versão do aplicativo foi lançada."
 *     ),
 *     @OA\Property(
 *         property="corpo",
 *         type="string",
 *         description="Corpo completo da notificação",
 *         example="Por favor, atualize seu aplicativo para aproveitar os novos recursos."
 *     ),
 *     @OA\Property(
 *         property="imagemDestaque",
 *         type="string",
 *         format="url",
 *         description="URL opcional da imagem de destaque",
 *         nullable=true,
 *         example="https://exemplo.com/imagem.png"
 *     )
 * )
 */
class NotificationRequest extends FormRequest
{
    public function rules(): array
    {
        return [
            'typeId' => 'required|integer|exists:notification_types,id',
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
