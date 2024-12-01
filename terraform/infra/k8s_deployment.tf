data "aws_ecr_repository" "repository" {
  name = "${var.project_name}-repository"
}


resource "kubernetes_deployment" "deployment" {
  depends_on = [kubernetes_secret.token_secret]
  metadata {
    name = "${var.project_name}-deployment"
  }
  spec {
    replicas = 2
    selector {
      match_labels = {
        app = var.project_name
      }
    }
    template {
      metadata {
        labels = {
          app = var.project_name
        }
      }
      spec {
        container {
          image = "${data.aws_ecr_repository.repository.repository_url}:${var.appversion}"
          name  = var.project_name
          port {
            container_port = 8080
          }
          resources {
            limits = {
              cpu    = "0.5"
              memory = "512Mi"
            }
            requests = {
              cpu    = "250m"
              memory = "50Mi"
            }
          }

          env {
            name  = "MONGODB_URL"
            value = "mongodb+srv://root:v1FgmWFh5YJqvdLE@fiap-postech.bn0pf.mongodb.net/fiap-postech?retryWrites=true&w=majority&appName=fiap-postech"
          }

          env {
            name = "TOKEN_SECRET"
            value_from {
              secret_key_ref {
                name = "${var.project_name}-token-secret"
                key  = "secret"
              }
            }
          }
          # liveness_probe {
          #   http_get {
          #     path = "/"
          #     port = 8080
          #   }

          #   initial_delay_seconds = 3
          #   period_seconds        = 3
          # }
        }
      }
    }
  }
}