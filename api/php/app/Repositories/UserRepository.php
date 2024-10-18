<?php

namespace App\Repositories;

use App\Models\User;

interface UserRepository
{
    public function create(array $data): User;
    public function findById(int $id): ?User;
    public function findByEmail(string $email): ?User;
}
