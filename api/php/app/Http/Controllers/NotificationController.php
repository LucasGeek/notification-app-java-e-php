<?php

namespace App\Http\Controllers;

use App\Http\Requests\NotificationRequest;
use App\Http\Requests\NotificationUpdateRequest;
use App\Services\NotificationService;
use App\Services\NotificationTypeService;
use Illuminate\Support\Facades\Auth;

/**
 * @OA\Tag(
 *     name="Notificações",
 *     description="APIs para gerenciamento de notificações"
 * )
 */
class NotificationController extends Controller
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
     *     path="/api/news/create",
     *     tags={"Notificações"},
     *     summary="Criar uma nova notificação",
     *     description="Cria uma notificação para o usuário autenticado.",
     *     security={{"bearerAuth":{}}},
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(ref="#/components/schemas/NotificationRequest")
     *     ),
     *     @OA\Response(response=201, description="Notificação criada"),
     *     @OA\Response(response=400, description="Erro ao criar notificação")
     * )
     */
    public function createNotification(NotificationRequest $request)
    {
        $user = Auth::user();

        $notificationType = $this->notificationTypeService->findById($request->typeId);

        if (!$notificationType) {
            return response()->json(['error' => 'Tipo de notificação não encontrado.'], 400);
        }

        $notification = $this->notificationService->create([
            'titulo' => $request->titulo,
            'descricao' => $request->descricao,
            'corpo' => $request->corpo,
            'imagem_destaque' => $request->imagemDestaque,
            'user_id' => $user->id,
            'notification_type_id' => $notificationType->id,
        ]);

        return response()->json($notification, 201);
    }

    /**
     * @OA\Get(
     *     path="/api/news/me",
     *     tags={"Notificações"},
     *     summary="Listar todas as notificações",
     *     description="Lista todas as notificações do usuário autenticado.",
     *     security={{"bearerAuth":{}}},
     *     @OA\Response(response=200, description="Listagem de notificações")
     * )
     */
    public function getNotificationsByUser()
    {
        $user = Auth::user();
        $notifications = $this->notificationService->getByUser($user->id);

        return response()->json($notifications);
    }

    /**
     * @OA\Get(
     *     path="/api/news/type/{typeId}",
     *     tags={"Notificações"},
     *     summary="Listar notificações por tipo",
     *     description="Lista todas as notificações do usuário autenticado por tipo.",
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(
     *         name="typeId",
     *         in="path",
     *         description="ID do tipo de notificação",
     *         required=true,
     *         @OA\Schema(type="integer")
     *     ),
     *     @OA\Response(response=200, description="Listagem de notificações por tipo"),
     *     @OA\Response(response=404, description="Tipo de notificação não encontrado"),
     *     @OA\Response(response=401, description="Usuário não autenticado")
     * )
     */
    public function getNotificationsByUserAndType($typeId)
    {
        $user = Auth::user();

        // Verifica se o tipo de notificação existe
        $notificationType = $this->notificationTypeService->findById($typeId);

        if (!$notificationType) {
            return response()->json(['error' => 'Tipo de notificação não encontrado.'], 404);
        }

        // Recupera as notificações do usuário autenticado por tipo
        $notifications = $this->notificationService->getByUserAndType($user->id, $notificationType->id);

        return response()->json($notifications);
    }


    /**
     * @OA\Put(
     *     path="/api/news/update/{newsId}",
     *     tags={"Notificações"},
     *     summary="Atualizar uma notificação",
     *     description="Atualiza uma notificação específica do usuário.",
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(
     *         name="newsId",
     *         in="path",
     *         description="ID da notificação",
     *         required=true,
     *         @OA\Schema(type="integer")
     *     ),
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(ref="#/components/schemas/NotificationUpdateRequest")
     *     ),
     *     @OA\Response(response=200, description="Notificação atualizada"),
     *     @OA\Response(response=404, description="Notificação não encontrada")
     * )
     */
    public function updateNotification($newsId, NotificationUpdateRequest $request)
    {
        $notification = $this->notificationService->findById($newsId);

        if (!$notification) {
            return response()->json(['error' => 'Notificação não encontrada.'], 404);
        }

        $updatedNotification = $this->notificationService->update($notification, [
            'titulo' => $request->titulo,
            'descricao' => $request->descricao,
            'corpo' => $request->corpo,
            'imagem_destaque' => $request->imagemDestaque,
        ]);

        return response()->json($updatedNotification);
    }

    /**
     * @OA\Delete(
     *     path="/api/news/delete/{newsId}",
     *     tags={"Notificações"},
     *     summary="Deletar uma notificação",
     *     description="Deleta uma notificação específica do usuário autenticado.",
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(
     *         name="newsId",
     *         in="path",
     *         description="ID da notificação a ser deletada",
     *         required=true,
     *         @OA\Schema(type="integer")
     *     ),
     *     @OA\Response(response=200, description="Notificação deletada com sucesso"),
     *     @OA\Response(response=404, description="Notificação não encontrada"),
     *     @OA\Response(response=401, description="Usuário não autenticado")
     * )
     */
    public function deleteNotification($newsId)
    {
        $user = Auth::user();

        $notification = $this->notificationService->findById($newsId);

        if (!$notification || $notification->user_id !== $user->id) {
            return response()->json(['error' => 'Notificação não encontrada ou não pertence ao usuário.'], 404);
        }

        $this->notificationService->delete($notification);

        return response()->json(['message' => 'Notificação deletada com sucesso.'], 200);
    }

}
