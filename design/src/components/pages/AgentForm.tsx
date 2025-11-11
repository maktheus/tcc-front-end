import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Card, CardContent, CardHeader, CardTitle } from '../ui/card';
import { Button } from '../ui/button';
import { Input } from '../ui/input';
import { Label } from '../ui/label';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '../ui/select';
import { ArrowLeft } from 'lucide-react';
import { toast } from 'sonner@2.0.3';

export function AgentForm() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    name: '',
    provider: '',
    endpoint: '',
    authType: 'none' as 'none' | 'bearer' | 'apikey',
    authToken: '',
  });

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    
    // Simulate save
    toast.success('Agente criado com sucesso!');
    setTimeout(() => {
      navigate('/agents');
    }, 500);
  };

  return (
    <div className="max-w-3xl space-y-6">
      <div className="flex items-center gap-4">
        <Button
          variant="ghost"
          size="sm"
          onClick={() => navigate('/agents')}
        >
          <ArrowLeft className="w-4 h-4 mr-2" />
          Voltar
        </Button>
      </div>

      <div>
        <h1>Novo Agente</h1>
        <p className="text-neutral-600 dark:text-neutral-400 mt-1">
          Cadastre um novo agente de IA na plataforma
        </p>
      </div>

      <form onSubmit={handleSubmit}>
        <Card>
          <CardHeader>
            <CardTitle>Informações do Agente</CardTitle>
          </CardHeader>
          <CardContent className="space-y-6">
            <div className="space-y-2">
              <Label htmlFor="name">Nome do Agente</Label>
              <Input
                id="name"
                placeholder="Ex: GPT-4 Agent"
                value={formData.name}
                onChange={(e) => setFormData({ ...formData, name: e.target.value })}
                required
              />
            </div>

            <div className="space-y-2">
              <Label htmlFor="provider">Provider</Label>
              <Input
                id="provider"
                placeholder="Ex: OpenAI, Anthropic, Custom"
                value={formData.provider}
                onChange={(e) => setFormData({ ...formData, provider: e.target.value })}
                required
              />
            </div>

            <div className="space-y-2">
              <Label htmlFor="endpoint">Endpoint URL</Label>
              <Input
                id="endpoint"
                type="url"
                placeholder="https://api.example.com/v1/chat"
                value={formData.endpoint}
                onChange={(e) => setFormData({ ...formData, endpoint: e.target.value })}
                required
              />
              <p className="text-neutral-500">
                URL completa do endpoint da API do agente
              </p>
            </div>

            <div className="space-y-2">
              <Label htmlFor="authType">Tipo de Autenticação</Label>
              <Select
                value={formData.authType}
                onValueChange={(value: 'none' | 'bearer' | 'apikey') =>
                  setFormData({ ...formData, authType: value })
                }
              >
                <SelectTrigger id="authType">
                  <SelectValue />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="none">Nenhuma</SelectItem>
                  <SelectItem value="bearer">Bearer Token</SelectItem>
                  <SelectItem value="apikey">API Key</SelectItem>
                </SelectContent>
              </Select>
            </div>

            {formData.authType !== 'none' && (
              <div className="space-y-2">
                <Label htmlFor="authToken">
                  {formData.authType === 'bearer' ? 'Bearer Token' : 'API Key'}
                </Label>
                <Input
                  id="authToken"
                  type="password"
                  placeholder="••••••••••••••••"
                  value={formData.authToken}
                  onChange={(e) => setFormData({ ...formData, authToken: e.target.value })}
                  required={formData.authType !== 'none'}
                />
                <p className="text-neutral-500">
                  Sua chave será armazenada de forma segura
                </p>
              </div>
            )}

            <div className="flex gap-3 pt-4 border-t border-neutral-200 dark:border-neutral-800">
              <Button type="submit">
                Criar Agente
              </Button>
              <Button
                type="button"
                variant="outline"
                onClick={() => navigate('/agents')}
              >
                Cancelar
              </Button>
            </div>
          </CardContent>
        </Card>
      </form>
    </div>
  );
}
