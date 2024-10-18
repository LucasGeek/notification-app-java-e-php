<?php

namespace App\Http\Controllers;

use App\Http\Requests\NotificationTypeUpdateRequest;
use App\Http\Requests\NotificationTypeRequest;
use App\Services\NotificationTypeService;
use App\Services\NotificationService;
use Illuminate\Support\Facades\Auth;

/**
 * @OA\Tag(
 *     name="Tipos de Notificações",
 *     description="APIs para gerenciamento de tipos de notificação"
 * )
 */
class NotificationTypeController extends Controller
{
    private NotificationService $notificationService;
    private NotificationTypeService $notificationTypeService;

    public function __construct(NotificationService $notificationService, NotificationTypeService $notificationTypeService)
    {
        $this->notificationService = $notificationService;
        $this->notificationTypeService = $notificationTypeService;
    }

    /**
     * @OA\Post(
     *     path="/api/type/create",
     *     tags={"Tipos de Notificações"},
     *     summary="Criar um novo tipo de notificação",
     *     description="Cria um novo tipo de notificação para o usuário autenticado.",
     *     security={{"bearerAuth":{}}},
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(ref="#/components/schemas/NotificationTypeRequest")
     *     ),
     *     @OA\Response(response=201, description="Tipo de notificação criado"),
     *     @OA\Response(response=400, description="Erro ao criar tipo de notificação")
     * )
     */
    public function createNotificationType(NotificationTypeRequest $request)
    {
        $user = Auth::user();

        $notificationType = $this->notificationTypeService->create([
            'nome_tipo' => $request->nomeTipo,
            'user_id' => $user->id,
        ]);

        return response()->json($notificationType, 201);
    }

    /**
     * @OA\Get(
     *     path="/api/type/me",
     *     tags={"Tipos de Notificações"},
     *     summary="Listar tipos de notificação do usuário",
     *     description="Lista todos os tipos de notificação do usuário autenticado.",
     *     security={{"bearerAuth":{}}},
     *     @OA\Response(response=200, description="Lista de tipos de notificação")
     * )
     */
    public function getNotificationTypesByUser()
    {
        $user = Auth::user();
        $notificationTypes = $this->notificationTypeService->getByUser($user->id);

        return response()->json($notificationTypes);
    }

    /**
     * @OA\Put(
     *     path="/api/type/update/{typeId}",
     *     tags={"Tipos de Notificações"},
     *     summary="Atualizar um tipo de notificação",
     *     description="Atualiza um tipo de notificação específico do usuário.",
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(
     *         name="typeId",
     *         in="path",
     *         description="ID do tipo de notificação",
     *         required=true,
     *         @OA\Schema(type="integer")
     *     ),
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(ref="#/components/schemas/NotificationTypeUpdateRequest")
     *     ),
     *     @OA\Response(response=200, description="Tipo de notificação atualizado"),
     *     @OA\Response(response=404, description="Tipo de notificação não encontrado")
     * )
     */
    public function updateNotificationType($typeId, NotificationTypeUpdateRequest $request)
    {
        $notificationType = $this->notificationTypeService->findById($typeId);

        if (!$notificationType) {
            return response()->json(['error' => 'Tipo de notificação não encontrado.'], 404);
        }

        $updatedNotificationType = $this->notificationTypeService->update($notificationType, [
            'nome_tipo' => $request->novoNomeTipo,
        ]);

        return response()->json(null, 204);;
    }

    /**
     * @OA\Delete(
     *     path="/api/type/delete/{typeId}",
     *     tags={"Tipos de Notificações"},
     *     summary="Deletar um tipo de notificação",
     *     description="Exclui um tipo de notificação específico do usuário.",
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(
     *         name="typeId",
     *         in="path",
     *         description="ID do tipo de notificação",
     *         required=true,
     *         @OA\Schema(type="integer")
     *     ),
     *     @OA\Response(response=204, description="Tipo de notificação excluído"),
     *     @OA\Response(response=404, description="Tipo de notificação não encontrado")
     * )
     */
    public function deleteNotificationType($typeId)
    {
        $user = Auth::user();

        $notificationType = $this->notificationTypeService->findById($typeId);

        if (!$notificationType) {
            return response()->json(['error' => 'Tipo de notificação não encontrado.'], 404);
        }

        // Verifica se há notificações referenciando este tipo de notificação
        $notificationsUsingType = $this->notificationService->getByUserAndType($user->id, $typeId);

        if (!empty($notificationsUsingType)) {
            return response()->json([
                'error' => 'Não é possível excluir o tipo de notificação, pois ele está sendo usado por notificações.'
            ], 400);
        }

        $this->notificationTypeService->delete($notificationType);

        return response()->json(null, 204);
    }
}
